// BarChartUtils.kt
package com.zybooks.practicepals.ui.barchart

import android.util.Log
import com.zybooks.practicepals.database.entities.PracticeLog
import java.text.SimpleDateFormat
import java.util.*

/**
 * Aggregates the total practice time per day for the last 7 days.
 * @param logs List of PracticeLog
 * @return Map where key is day of week (e.g., "Mon") and value is total practice time in minutes
 */
fun aggregatePracticeTimePerDay(logs: List<PracticeLog>): Map<String, Long> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())

    val last7DaysMap = LinkedHashMap<String, Long>()
    for (i in 0..6) {
        val day = dateFormat.format(calendar.time)
        last7DaysMap[day] = 0L
        calendar.add(Calendar.DAY_OF_YEAR, -1)
    }

    logs.forEach { log ->
        val logDate = Date(log.dateLogged)
        val logDay = dateFormat.format(logDate)
        if (last7DaysMap.containsKey(logDay)) {
            last7DaysMap[logDay] = last7DaysMap[logDay]!! + (log.timeLogged / 60000) // Convert ms to minutes
        }
    }

    Log.d("BarChartUtils", "Aggregated Data: $last7DaysMap")
    return last7DaysMap.toMap().toSortedMap(compareByDescending {
        dateFormat.parse(it)?.time ?: 0
    })
}
