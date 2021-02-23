package com.example.githubrepositories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepositories.data.model.Repository
import com.example.githubrepositories.data.repository.GithubRepository
import com.example.githubrepositories.utils.CommonHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
* <h1>GitHubRepositoryViewModel</h1>
* <p>
*   Responsible for all operations related to GitHub repositories
* </p>
*
* @author Tonmoy Chandra Ray
* */
class GitHubRepositoryViewModel(private val gitHubRepository: GithubRepository) : ViewModel(){

    private val LOG_TAG = "GitHubRepositoryViewModel"

    private var currentQueryValue: String? = null
    var currentSearchResult: Flow<PagingData<Repository>>? = null

    var maxContributor: MutableLiveData<Repository> = MutableLiveData()
        private set



    fun searchRepo(queryString: String): Flow<PagingData<Repository>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Repository>> = gitHubRepository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun getMaxContributor(repo: Repository){
        CommonHelper.printLog(LOG_TAG, " getMaxContributor $repo")

        viewModelScope.launch {
            maxContributor.postValue(gitHubRepository.getMaxContributor(repo))
        }
    }

}