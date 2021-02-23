package com.example.githubrepositories.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubrepositories.data.repository.GithubRepository
import com.example.githubrepositories.viewmodel.GitHubRepositoryViewModel

/**
 * Custom ViewModelFactory
 *
 * Ref: https://medium.com/koderlabs/viewmodel-with-viewmodelprovider-factory-the-creator-of-viewmodel-8fabfec1aa4f
 */
class ViewModelFactory(private val repository: GithubRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GitHubRepositoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GitHubRepositoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
