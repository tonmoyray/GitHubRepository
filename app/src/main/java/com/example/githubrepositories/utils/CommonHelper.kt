package com.example.githubrepositories.utils

import com.example.githubrepositories.BuildConfig
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
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

    fun getRepoOwner(fullName : String) : String{
        return fullName.split("/")[0]
    }

    /**
     * Method for converting UTC Date to Long
     * @param date in this format -> 2020-08-31T07:43:13Z
     * @return timestamp in this format -> 1597921074042
     * @usage for converting Diagnostics data created & update time
     * @author Tonmoy Chandra Ray
     * */
    fun utcToLong(date: String) : Long {
        try {
            val format = SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss'Z'", Locale.US)
            format.setTimeZone(TimeZone.getTimeZone("UTC"))
            val parsedDate: Date = format.parse(date)
            return parsedDate.time
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
        return  0L
    }

    /**
     *
     * @param value is a timeStamp
     * @return formatted date ex: String Jan 1, 2019
     * @author Tonmoy Chandra Ray
     * */
    fun monthDayHourMin(value: Long):String{
        val stamp = Timestamp(value)
        val date = java.sql.Date(stamp.time)
        val day = SimpleDateFormat("MMM d,YYYY", Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.time = date
        return day.format(cal.time)
    }
}