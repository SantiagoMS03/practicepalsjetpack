package com.zybooks.practicepals.ui.metronome

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.practicepals.R
import com.zybooks.practicepals.utilities.Metronome
import com.zybooks.practicepals.viewmodel.MetronomeViewModel

@Composable
fun MetronomeScreen(viewModel: MetronomeViewModel = viewModel()) {
    val tempo by viewModel.tempo.collectAsState()
    val timeSignature by viewModel.timeSignature.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Metronome", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Tempo Display and Controls
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = viewModel::decreaseTempo) {
                Icon(painterResource(id = R.drawable.ic_minus), contentDescription = "Decrease BPM")
            }

            Text(text = "$tempo BPM", fontSize = 32.sp, modifier = Modifier.padding(horizontal = 16.dp))

            IconButton(onClick = viewModel::increaseTempo) {
                Icon(painterResource(id = R.drawable.ic_plus), contentDescription = "Increase BPM")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time Signature Display
        Text(text = "Time Signature: $timeSignature", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Play/Pause Button
        IconButton(
            onClick = { viewModel.togglePlayPause() },
            modifier = Modifier.size(80.dp)
        ) {
            Icon(
                painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                contentDescription = if (isPlaying) "Pause" else "Play",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}
