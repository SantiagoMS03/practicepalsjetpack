package com.zybooks.practicepals.ui.stopwatch

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.practicepals.R
import com.zybooks.practicepals.ui.practicelogs.NewPracticeLogDialog
import com.zybooks.practicepals.ui.utilities.ContinueDialog
import com.zybooks.practicepals.viewmodel.StopwatchViewModel
import kotlin.math.floor

@Composable
fun StopwatchScreen(viewModel: StopwatchViewModel = viewModel()) {
    val elapsedTime by viewModel.elapsedTime.collectAsState()

    // Formatting the time
    val minutes = (elapsedTime / 60000).toInt()
    val seconds = ((elapsedTime % 60000) / 1000).toInt()
    val milliseconds = ((elapsedTime % 1000) / 10).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Time Display
        Text(
            text = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds),
            fontSize = 48.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons Row (Play/Pause and Stop)
        Row(horizontalArrangement = Arrangement.Center) {
            // Play/Pause Button
            Button(
                onClick = {
                    if (viewModel.isRunning()) viewModel.pauseStopwatch() else viewModel.startStopwatch()
                }
            ) {
                Text(if (viewModel.isRunning()) "Pause" else if (viewModel.hasStarted()) "Continue" else "Start")
            }

            Button(
                onClick = {
                    if (viewModel.isRunning()) { viewModel.pauseStopwatch() }
                    NewPracticeLogDialog(
                        elapsedTime = elapsedTime,
                        pieceId = /* TODO */
                    )
                }
            ) {
                Text("Stop")
            }

        }
    }
}
