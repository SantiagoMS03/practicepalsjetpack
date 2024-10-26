package com.zybooks.practicepals.ui.metronome

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.zybooks.practicepals.R
import com.zybooks.practicepals.utilities.Metronome

@Composable
fun TimeSignatureDialog(
    onDismiss: () -> Unit,
    onSetNumerator: (Int) -> Unit,
    initialNumerator: Int,
    minBeats: Int = 2,
    maxBeats: Int = 16
) {
    var numerator by remember { mutableStateOf(initialNumerator) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set Time Signature") },
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { numerator = (numerator - 1).coerceAtLeast(minBeats) }) {
                    Icon(painterResource(id = R.drawable.ic_minus), contentDescription = "Decrease")
                }
                Text("$numerator/4", fontSize = 32.sp)
                IconButton(onClick = { numerator = (numerator + 1).coerceAtMost(maxBeats) }) {
                    Icon(painterResource(id = R.drawable.ic_plus), contentDescription = "Increase")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSetNumerator(numerator)
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
