package com.example.githubrepositories.data.remote

import com.example.githubrepositories.BuildConfig
import com.example.githubrepositories.data.remote.response.RepositoryContributionResponse
import com.example.githubrepositories.data.remote.response.RepositorySearchResponse
import com.example.githubrepositories.utils.ApiRequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * <h1>ApiService</h1>
 * <p>
 * An interface with all network calls & retrofit instance
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
interface GitHubApiService {

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GitHubApiService {

            val client = OkHttpClient.Builder()
            client.addInterceptor(ApiRequestInterceptor())

            if(BuildConfig.DEBUG){
                val logger = HttpLoggingInterceptor()
                logger.level = Level.BODY
                client.addInterceptor(logger)
            }

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubApiService::class.java)
        }
    }


    /**
     * Get repos by search string.
     */
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepositorySearchResponse


    /**
     * Get contributors by stats
     */
    @GET("repos/{owner}/{project}/stats/contributors")
    suspend fun searchContributions(
        @Path("owner") name: String,
        @Path("project") project: String
    ): List<RepositoryContributionResponse>?
}