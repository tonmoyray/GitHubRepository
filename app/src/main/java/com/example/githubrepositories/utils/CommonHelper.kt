package com.example.githubrepositories.utils

import com.example.githubrepositories.BuildConfig
import java.util.logging.Level
import java.util.logging.Logger

/**
 * <h1>CommonHelper</h1>
 * <p>
 *  All common function are kept here
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
object CommonHelper {

    private lateinit var log: Logger

    fun initLogger() {
        log = Logger.getLogger("DEBUG")
        if (BuildConfig.DEBUG) {
            log.level = Level.ALL
        } else {
            log.level = Level.OFF
        }
    }
    fun printLog(TAG: String, message: String) {
        log.info("$TAG $message")
    }

    fun warningLog(TAG: String, message: String) {
        log.log(Level.WARNING, "$TAG $message")
    }

    fun warningLog(TAG: String, message: String, t: Throwable?) {
        log.log(Level.WARNING, "$TAG $message", t)
    }
}