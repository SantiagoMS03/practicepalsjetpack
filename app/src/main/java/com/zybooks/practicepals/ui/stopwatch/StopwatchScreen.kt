package com.zybooks.practicepals.ui.stopwatch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zybooks.practicepals.viewmodel.StopwatchViewModel
import com.zybooks.practicepals.viewmodel.PieceViewModel
import com.zybooks.practicepals.ui.practicelogs.NewPracticeLogDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopwatchScreen(
    stopwatchViewModel: StopwatchViewModel = hiltViewModel(),
    pieceViewModel: PieceViewModel = hiltViewModel()
) {
    val elapsedTime by stopwatchViewModel.elapsedTime.collectAsState()

    // Time formatting for display
    val minutes = (elapsedTime / 60000).toInt()
    val seconds = ((elapsedTime % 60000) / 1000).toInt()
    val milliseconds = ((elapsedTime % 1000) / 10).toInt()

    // State variables
    val pieces by pieceViewModel.piecesFlow.collectAsState(emptyList())
    var selectedPieceId by remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

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

        Spacer(modifier = Modifier.height(16.dp))

        // Piece Selection Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = pieces.find { it.pieceId == selectedPieceId }?.name ?: "Select Piece",
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Piece") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clickable { expanded = true }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                pieces.forEach { piece ->
                    DropdownMenuItem(
                        text = { Text(piece.name) },
                        onClick = {
                            selectedPieceId = piece.pieceId
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Start/Pause and Stop Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = {
                    if (stopwatchViewModel.isRunning()) stopwatchViewModel.pauseStopwatch()
                    else stopwatchViewModel.startStopwatch()
                }
            ) {
                Text(if (stopwatchViewModel.isRunning()) "Pause" else "Start")
            }

            Button(
                onClick = {
                    if (stopwatchViewModel.isRunning()) stopwatchViewModel.pauseStopwatch()
                    if (selectedPieceId != null) showDialog = true  // Only open dialog if a piece is selected
                },
                enabled = selectedPieceId != null
            ) {
                Text("Stop")
            }
        }
    }

    // Show NewPracticeLogDialog when the stopwatch is stopped
    if (showDialog) {
        NewPracticeLogDialog(
            elapsedTime = elapsedTime,
            pieceId = selectedPieceId!!,
            onPracticeLogAdded = {
                showDialog = false
                stopwatchViewModel.resetStopwatch()
            },
            onDismissRequest = { showDialog = false }
        )
    }
}
