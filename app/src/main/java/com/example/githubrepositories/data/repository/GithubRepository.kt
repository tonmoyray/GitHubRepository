package com.example.githubrepositories.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.githubrepositories.data.model.Repository
import com.example.githubrepositories.data.paging.RepositorySearchPagingSource
import com.example.githubrepositories.data.remote.GitHubApiService
import com.example.githubrepositories.data.remote.response.Contribution
import com.example.githubrepositories.utils.CommonHelper
import com.example.githubrepositories.utils.exception.NoContentException
import kotlinx.coroutines.flow.Flow

/**
 * <h1>GithubRepository</h1>
 * <p>
 *  Repository class that works with local and remote data sources.
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class GithubRepository(private val apiService: GitHubApiService) {
    private val LOG_TAG = "GithubRepository"

    fun getSearchResultStream(query: String): Flow<PagingData<Repository>> {

        CommonHelper.printLog(LOG_TAG, " getSearchResultStream $query , apiService $apiService")

        return  Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { RepositorySearchPagingSource(apiService, query) }
        ).flow
    }

    suspend fun getMaxContributor(repo : Repository) : Repository {

        try{
            val contributionResponse = apiService.searchContributions(
                    CommonHelper.getRepoOwner(repo.fullName),
                    repo.name)

            if(!contributionResponse.isNullOrEmpty()){
                val contribution = contributionResponse[0]
                var max = 0L
                var maxCount = Contribution(0L, 0L, 0L, 0L)
                contribution.items.forEach { count ->
                    val total = count.c + count.d + count.a
                    if (total > max) {
                        max = total
                        maxCount = count
                    }
                }
                repo.countribution = maxCount
                return repo
            }else{
                repo.countributionNotAvailable = true
            }
        }catch (e : NoContentException){ //TODO: cause no json response. Is there a better way to handle this ?
            repo.countributionNotAvailable = true
        }catch (e : Exception){  //TODO: cause empty json response {}. Is there a better way to handle this ?
            repo.countributionNotAvailable = true
        }
        return repo
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}
