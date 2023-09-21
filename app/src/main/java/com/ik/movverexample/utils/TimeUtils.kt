package com.ik.movverexample.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun getRelativeTime(timeString: String, currentTime: Date = Date()): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
        val date = sdf.parse(timeString) ?: return "Unknown time"

        val diff = currentTime.time - date.time

        val second = 1000
        val minute = 60 * second
        val hour = 60 * minute
        val day = 24 * hour

        return when {
            diff < minute -> "Now"  // Show "Now" for less than a minute
            diff < hour -> "${diff / minute} minutes ago"
            diff < day -> "${diff / hour} hours ago"
            else -> "${diff / day} days ago"
        }
    }
}