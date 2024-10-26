package com.zybooks.practicepals.ui.metronome

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import com.zybooks.practicepals.utilities.Metronome

@Composable
fun BpmKeypadDialog(
    onDismiss: () -> Unit,
    onSetBpm: (Int) -> Unit,
    initialBpm: Int
) {
    var bpmText by remember { mutableStateOf(initialBpm.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set BPM") },
        text = {
            OutlinedTextField(
                value = bpmText,
                onValueChange = { bpmText = it.filter { char -> char.isDigit() }.take(3) },
                label = { Text("BPM") }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                val bpm = bpmText.toIntOrNull() ?: initialBpm
                onSetBpm(bpm.coerceIn(Metronome.MIN_METRONOME_BPM, Metronome.MAX_METRONOME_BPM))
                onDismiss()
            }) {
                Text("Set")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
