// PracticeBarChartScreen.kt
package com.zybooks.practicepals.ui.barchart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.practicepals.viewmodel.PracticeLogViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import android.util.Log

@Composable
fun PracticeBarChartScreen(
    practiceLogViewModel: PracticeLogViewModel = hiltViewModel()
) {
    // Collect practice logs from the last 7 days
    val practiceLogs by practiceLogViewModel.getPracticeLogsLast7Days().collectAsState(initial = emptyList())

    Log.d("PracticeBarChartScreen", "Received ${practiceLogs.size} practice logs")

    // Aggregate practice time per day
    val aggregatedData = remember(practiceLogs) {
        aggregatePracticeTimePerDay(practiceLogs)
    }

    Log.d("PracticeBarChartScreen", "Aggregated Data: $aggregatedData")

    // Calculate the 7-day average
    val averagePracticeTime by remember(aggregatedData) {
        mutableStateOf(
            if (aggregatedData.isNotEmpty()) {
                aggregatedData.values.sum() / aggregatedData.size
            } else {
                0L
            }
        )
    }

    Log.d("PracticeBarChartScreen", "7-Day Average: $averagePracticeTime minutes")

    // UI Layout
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Practice Time (Last 7 Days)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (practiceLogs.isEmpty()) {
                Text(
                    text = "No practice logs for the last 7 days.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 50.dp)
                )
            } else {
                BarChart(
                    data = aggregatedData,
                    average = averagePracticeTime,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }
    }
}
