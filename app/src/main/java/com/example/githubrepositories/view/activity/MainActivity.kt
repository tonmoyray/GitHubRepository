package com.example.githubrepositories.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.githubrepositories.R
import com.example.githubrepositories.databinding.ActivityMainBinding
import com.example.githubrepositories.viewmodel.GitHubRepositoryViewModel

/**
* <h1>MainActivity</h1>
* <p>
*   The launcher actiMvity of this application for now.
* </p>
*
* @author Tonmoy Chandra Ray
* */
class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.simpleName
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel: GitHubRepositoryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }
}