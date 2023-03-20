package com.aroniez.futaa.utils

import android.content.Context
import android.text.format.DateUtils
import com.aroniez.futaa.R
import java.text.SimpleDateFormat
import java.util.*


object DateTimeUtil {

    fun getRelativeTime(dateTimestamp: Long): String {
        val nowTimestamp = System.currentTimeMillis()
        return DateUtils.getRelativeTimeSpanString(dateTimestamp, nowTimestamp, 0L, DateUtils.FORMAT_ABBREV_RELATIVE).toString()
    }

    fun getRelativeDate(rawDate: String, context: Context): String {
        return when (rawDate) {
            getCustomDate(0) -> context.getString(R.string.date_today)
            getCustomDate(1) -> context.getString(R.string.date_tomorrow)
            getCustomDate(-1) -> context.getString(R.string.date_yesterday)
            else -> rawDate
        }
    }

    private fun convertUTCToLocalTime(dateTime: String) {
        val df = SimpleDateFormat("MMM dd,yyyy HH:mm:ss a", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(dateTime)
        df.timeZone = TimeZone.getDefault()
        val formattedDate = df.format(date)
    }

    fun getCurrentTime(): String {
        val todayDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("hh", Locale.getDefault())
        return formatter.format(todayDate)
    }


    fun getCustomDate(daysFromToday: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysFromToday)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(calendar.time)
    }


    fun getDateCurrentTimeZone(timestamp: Long): String {
        try {
            val calendar = Calendar.getInstance()
            val tz = TimeZone.getDefault()
            calendar.timeInMillis = timestamp * 1000
            //calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
            val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val currenTimeZone = calendar.time as Date
            return sdf.format(currenTimeZone)
        } catch (e: Exception) {
        }
        return ""
    }
}