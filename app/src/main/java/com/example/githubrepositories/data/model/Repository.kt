package com.example.githubrepositories.data.model

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
    @SerializedName("name") val name: String
    )
