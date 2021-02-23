package com.example.githubrepositories.utils

import android.util.Log
import com.example.githubrepositories.utils.exception.NoContentException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * <h1>ApiRequestInterceptor</h1>
 * <p>
 * This interceptor will be called for every request. It will check if the response if of 204 with
 * no content.
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class ApiRequestInterceptor() : Interceptor  {
    private val LOG_TAG = "ApiRequestInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)
        CommonHelper.printLog(LOG_TAG," ApiRequestInterceptor response code "+response.code)
        if (response.code == 204) {
            throw NoContentException("No Content")
        }
        return response
    }
}