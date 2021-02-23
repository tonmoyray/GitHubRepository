package com.example.githubrepositories.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    /**
     * Method for hiding soft keypad in GUI
     * @param context
     * @param view
     * @usage hideSoftKeyBoard(context, view)
     * @author Tonmoy Ray
     * */
    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}