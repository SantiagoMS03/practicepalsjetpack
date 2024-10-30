package com.zybooks.practicepals.ui.metronome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.practicepals.R
import com.zybooks.practicepals.viewmodel.MetronomeViewModel
import com.zybooks.practicepals.viewmodel.PieceViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetronomeScreen(
    metronomeViewModel: MetronomeViewModel = viewModel(),
    pieceViewModel: PieceViewModel = hiltViewModel()
) {
    val tempo by metronomeViewModel.tempo.collectAsState()
    val timeSignature by metronomeViewModel.timeSignature.collectAsState()
    val isPlaying by metronomeViewModel.isPlaying.collectAsState()
    val showBpmDialog by metronomeViewModel.showBpmDialog.collectAsState()
    val showTimeSignatureDialog by metronomeViewModel.showTimeSignatureDialog.collectAsState()
    val pieces by pieceViewModel.piecesFlow.collectAsState(emptyList())

    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedPiece by remember { mutableStateOf<String?>(null) }

    val filteredPieces = pieces.filter { it.name.contains(searchText, ignoreCase = true) }

    // Get the focus manager to control the keyboard
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {  // Replace clickable with pointerInput to remove ripple
                detectTapGestures(onTap = {
                    focusManager.clearFocus()  // Dismiss keyboard on outside click
                })
            }

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),  // Center-align the content
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Metronome", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Exposed Dropdown for selecting a piece
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        expanded = true  // Open dropdown as user types
                    },
                    label = { Text("Select Piece") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()  // Aligns dropdown with TextField
                )

                // Dropdown Menu with filtered pieces
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                        focusManager.clearFocus()  // Dismiss keyboard when menu closes
                    }
                ) {
                    filteredPieces.forEach { piece ->
                        DropdownMenuItem(
                            text = { Text(piece.name) },
                            onClick = {
                                selectedPiece = piece.name
                                searchText = piece.name  // Set search text to selected name
                                expanded = false
                                focusManager.clearFocus()  // Hide keyboard on selection
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tempo Display and Controls
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = metronomeViewModel::decreaseTempo) {
                    Icon(painterResource(id = R.drawable.ic_minus), contentDescription = "Decrease BPM")
                }

                Text(
                    text = "$tempo BPM",
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { metronomeViewModel.setShowBpmDialog(true) }
                )

                IconButton(onClick = metronomeViewModel::increaseTempo) {
                    Icon(painterResource(id = R.drawable.ic_plus), contentDescription = "Increase BPM")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Time Signature Display
            Text(
                text = "Time Signature: $timeSignature",
                fontSize = 20.sp,
                modifier = Modifier.clickable { metronomeViewModel.setShowTimeSignatureDialog(true) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Play/Pause Button
            IconButton(
                onClick = { metronomeViewModel.togglePlayPause() },
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

    // BPM Dialog
    if (showBpmDialog) {
        BpmKeypadDialog(
            onDismiss = { metronomeViewModel.setShowBpmDialog(false) },
            onSetBpm = { bpm -> metronomeViewModel.setTempo(bpm) },
            initialBpm = tempo
        )
    }

    // Time Signature Dialog
    if (showTimeSignatureDialog) {
        TimeSignatureDialog(
            onDismiss = { metronomeViewModel.setShowTimeSignatureDialog(false) },
            onSetNumerator = { numerator -> metronomeViewModel.setTimeSignature(numerator) },
            initialNumerator = timeSignature.split("/")[0].toInt()
        )
    }
}
