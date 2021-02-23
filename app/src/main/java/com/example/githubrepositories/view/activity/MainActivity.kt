package com.example.githubrepositories.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import com.example.githubrepositories.R
import com.example.githubrepositories.data.model.Repository
import com.example.githubrepositories.data.remote.GitHubApiService
import com.example.githubrepositories.data.repository.GithubRepository
import com.example.githubrepositories.databinding.ActivityMainBinding
import com.example.githubrepositories.utils.CommonHelper
import com.example.githubrepositories.utils.ViewModelFactory
import com.example.githubrepositories.view.adapter.RepositoryAdapter
import com.example.githubrepositories.viewmodel.GitHubRepositoryViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
    private val adapter = RepositoryAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelFactory(GithubRepository(GitHubApiService.create())))
            .get(GitHubRepositoryViewModel::class.java)

        initWidget()
        initObserver()
        search("tonmoy") //TODO for testing
    }

    private var searchJob: Job? = null
    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query)
        }
    }

    private fun initWidget() {
        activityMainBinding.list.adapter = adapter
    }

    private fun initObserver() {

        viewModel.currentSearchResult?.observe(this, Observer {
            CommonHelper.printLog(LOG_TAG, " result repository $it")
            adapter.submitData(this.lifecycle,it)
        })

        viewModel.maxContributor.observe(this, Observer { updatedRepo ->

            val pagingData = viewModel.currentSearchResult?.value

            pagingData?.map {
                if (updatedRepo.position == it.position){
                    CommonHelper.printLog(LOG_TAG, " updated repository $it")
                    return@map it.copy(countribution = updatedRepo.countribution)
                }
                else return@map it
            }
            adapter.notifyItemChanged(updatedRepo.position)
        })
    }

    override fun fetchMaxContributor(repo: Repository) {
       // viewModel.getMaxContributor(repo)
    }
}