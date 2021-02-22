package com.example.githubrepositories

import android.app.Application
import com.example.githubrepositories.utils.CommonHelper

/**
 * <h1>App</h1>
 * <p>
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class Application : Application(){

    override fun onCreate() {
        super.onCreate()
        CommonHelper.initLogger()
    }

}