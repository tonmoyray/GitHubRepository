package com.example.githubrepositories.data.remote.response

import com.example.githubrepositories.data.model.Repository
import com.google.gson.annotations.SerializedName

/**
 * <h1>RepositorySearchResponse</h1>
 * <p>
 * The API response model for repository search
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
data class RepositorySearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Repository> = emptyList(),
    val nextPage: Int? = null
)
