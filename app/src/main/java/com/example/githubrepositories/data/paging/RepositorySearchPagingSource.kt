package com.example.githubrepositories.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubrepositories.data.model.Repository
import com.example.githubrepositories.data.remote.GitHubApiService
import com.example.githubrepositories.data.repository.GithubRepository
import com.example.githubrepositories.utils.CommonHelper
import retrofit2.HttpException
import java.io.IOException


/**
 * <h1>RepositorySearchPagingSource</h1>
 * <p>
 * Android Paging implementation
 * </p>
 * Ref: https://developer.android.com/topic/libraries/architecture/paging
 */
class RepositorySearchPagingSource (private val service: GitHubApiService, private val query: String)
    : PagingSource<Int, Repository>() {

    private val LOG_TAG = "RepositorySearchPagingSource"
    private val STARTING_PAGE_INDEX = 1

    init {
        CommonHelper.printLog(LOG_TAG, " init $query")

    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {

        val position = params.key ?: STARTING_PAGE_INDEX
        val apiQuery = query

        CommonHelper.printLog(LOG_TAG, " position:$position  apiQuery:$apiQuery ")

        return try {
            val response = service.searchRepos(apiQuery, position, params.loadSize)

            CommonHelper.printLog(LOG_TAG, " searchRepos response:$response ")

            val repos = response.items

            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / GithubRepository.NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {

        CommonHelper.printLog(LOG_TAG, " getRefreshKey: ")

        return null
    }
}