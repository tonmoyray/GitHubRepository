package com.example.githubrepositories.data.model

import com.example.githubrepositories.data.remote.response.Contribution
import com.google.gson.annotations.SerializedName

/**
 * <h1>Repository</h1>
 * <p>
 * A model of Github repository with all necessary properties for our usecase
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
data class Repository(
        @field:SerializedName("id") val id: Long,
        @field:SerializedName("name") val name: String,
        @field:SerializedName("full_name") val fullName: String,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("html_url") val url: String,
        @field:SerializedName("language") val language: String?,
        @field:SerializedName("updated_at") val updatedAt: String?,
        var countribution: Contribution?,
        var position: Int,
        var countributionNotAvailable: Boolean = false
    )
