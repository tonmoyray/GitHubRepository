package com.example.githubrepositories.data.remote

import com.example.githubrepositories.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
}