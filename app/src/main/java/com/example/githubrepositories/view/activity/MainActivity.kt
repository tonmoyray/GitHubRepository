package com.example.githubrepositories.view.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.map
import com.example.githubrepositories.R
import com.example.githubrepositories.data.model.Repository
import com.example.githubrepositories.data.remote.GitHubApiService
import com.example.githubrepositories.data.repository.GithubRepository
import com.example.githubrepositories.databinding.ActivityMainBinding
import com.example.githubrepositories.utils.AutoFitGridLayoutManager
import com.example.githubrepositories.utils.CommonHelper
import com.example.githubrepositories.utils.CustomToast
import com.example.githubrepositories.utils.ViewModelFactory
import com.example.githubrepositories.view.adapter.RepositoryAdapter
import com.example.githubrepositories.viewmodel.GitHubRepositoryViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
* <h1>MainActivity</h1>
* <p>
*   The launcher actiMvity of this application for now.
* </p>
*
* @author Tonmoy Chandra Ray
* */
class MainActivity : AppCompatActivity(), RepositoryAdapter.AdapterListener {

    private val LOG_TAG = "MainActivity"
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: GitHubRepositoryViewModel
    private lateinit var adapter : RepositoryAdapter

    private var searchJob: Job? = null
    private var query : String = ""

    private var doubleBackToExitPressedOnce = false
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelFactory(GithubRepository(GitHubApiService.create())))
            .get(GitHubRepositoryViewModel::class.java)

        query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: ""
        initWidget()
        initObserver()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, activityMainBinding.searchRepo.text.trim().toString())
    }


    private fun search(query: String) {
        CommonHelper.hideSoftKeyBoard(this, activityMainBinding.root)
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initWidget() {
        adapter = RepositoryAdapter(this)
        activityMainBinding.list.layoutManager = AutoFitGridLayoutManager(this, 1000)
        activityMainBinding.list.adapter = adapter
        adapter.addLoadStateListener { loadState ->
            activityMainBinding.list.isVisible = loadState.source.refresh is LoadState.NotLoading
            activityMainBinding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            activityMainBinding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            if( adapter.itemCount == 0
                && loadState.source.refresh !is LoadState.Loading){
                activityMainBinding.empty.visibility =  View.VISIBLE
            }else{
                activityMainBinding.empty.visibility =  View.INVISIBLE
            }
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                CustomToast.makeText(this, "Error ${it.error}", Toast.LENGTH_LONG).show()
            }
        }
        initSearch()
    }

    private fun initSearch() {
        activityMainBinding.searchRepo.setText(query)

        activityMainBinding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        activityMainBinding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                    // Only emit when REFRESH LoadState for RemoteMediator changes.
                    .distinctUntilChangedBy { it.refresh }
                    // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { activityMainBinding.list.scrollToPosition(0) }
        }

        activityMainBinding.searchRepo.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                activityMainBinding.searchIcon.visibility = View.GONE
            } else {
                activityMainBinding.searchIcon.visibility = View.VISIBLE
            }
        }
        if(query.isNotEmpty()){
            search(query)
        }
    }

    private fun updateRepoListFromInput() {
        activityMainBinding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                activityMainBinding.list.scrollToPosition(0)
                search(it.toString())
            }
        }
    }

    private fun initObserver() {
        viewModel.maxContributor.observe(this, Observer { updatedRepo ->

            val pagingData = viewModel.currentSearchResult?.asLiveData()?.value

            pagingData?.map {
                if (updatedRepo.position == it.position) {
                    CommonHelper.printLog(LOG_TAG, " updated repository $it")
                    return@map it.copy(countribution = updatedRepo.countribution)
                } else return@map it
            }
            adapter.notifyItemChanged(updatedRepo.position)
        })
    }

    override fun fetchMaxContributor(repo: Repository) {
        viewModel.getMaxContributor(repo)
    }

    /**
     * Method for exit the app
     * @see doubleBackToExitPressedOnce boolean if true on press exit the app
     * Reset [doubleBackToExitPressedOnce] on 3 second delay
     */
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        CustomToast.makeText(this, getString(R.string.exit_toast), Toast.LENGTH_SHORT).show()

        coroutineScope.launch {
            delay(3000L)
            doubleBackToExitPressedOnce = false
        }

    }
}