package com.zybooks.practicepals.ui.practicelogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.zybooks.practicepals.database.entities.PracticeLog
import com.zybooks.practicepals.viewmodel.PieceViewModel
import com.zybooks.practicepals.viewmodel.PracticeLogViewModel

@Composable
fun NewPracticeLogDialog (
    practiceLogViewModel: PracticeLogViewModel = hiltViewModel(),
    pieceViewModel: PieceViewModel = hiltViewModel(),
    onPracticeLogAdded: () -> Unit,
    onDismissRequest: () -> Unit,
    elapsedTime: Long,
    pieceId: Int
) {
    var description by remember { mutableStateOf("") }
    val pieceFlow = remember(pieceId) { pieceViewModel.getPieceDetails(pieceId) }
    val piece by pieceFlow.collectAsState(initial = null)

    piece?.let {
        Dialog(
            onDismissRequest = onDismissRequest,
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true, ),
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .background(color = Color.White)) {
                Text(text = "Elapsed time: $elapsedTime" + "$elapsedTime")
                Text(text = "Piece name: ${it.name}")

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Short practice session description") },
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Button(onClick =
                {
                    practiceLogViewModel.addPracticeLog(
                        PracticeLog(
                            pieceId = pieceId,
                            timeLogged = elapsedTime,
                            practiceLogDescription = description
                        )
                    )
                    onPracticeLogAdded()
                }) {
                    Text(text = "Save")
                }
            }
        }

    } ?: run {
        Text(text = "Loading...", modifier = Modifier.padding(16.dp))
    }




}