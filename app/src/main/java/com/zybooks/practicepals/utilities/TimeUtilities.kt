package com.zybooks.practicepals.utilities

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Converts milliseconds to a formatted string "Xm Ys" or "Xm" if seconds are zero.
 */
fun Long.toFormattedTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return if (seconds > 0) {
        "${minutes}m ${seconds}s"
    } else {
        "${minutes}m"
    }
}

/**
 * Converts milliseconds to a formatted date string "MMM dd, yyyy" or similar.
 */
fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(this)
}
