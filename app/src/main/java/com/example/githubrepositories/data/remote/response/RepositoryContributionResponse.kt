package com.example.githubrepositories.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * <h1>RepositoryContributionResponse</h1>
 * <p>
 * The API response model for repository contribution query
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
data class RepositoryContributionResponse(
        @SerializedName("total") val total: Int = 0,
        @SerializedName("weeks") val items: List<Contribution> = emptyList()
)

data class Contribution(
        @field:SerializedName("w") val w: Long,
        @field:SerializedName("a") val a: Long,
        @field:SerializedName("d") val d: Long,
        @field:SerializedName("c") val c: Long
)
