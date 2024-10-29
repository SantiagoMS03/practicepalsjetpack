package com.zybooks.practicepals.ui.pieces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zybooks.practicepals.database.entities.Piece
import com.zybooks.practicepals.viewmodel.PieceViewModel

@Composable
fun NewPieceScreen(
    viewModel: PieceViewModel = hiltViewModel(),
    onPieceAdded: () -> Unit // Callback to navigate back after adding
) {
    var name by remember { mutableStateOf("") }
    var composer by remember { mutableStateOf("") }
    var totalTimePracticed by remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Piece Name") },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = composer,
                onValueChange = { composer = it },
                label = { Text("Composer") },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = totalTimePracticed,
                onValueChange = { totalTimePracticed = it },
                label = { Text("Total Time Practiced (minutes)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Button(
                onClick = {
                    val piece = Piece(
                        name = name,
                        composer = composer,
                        totalTimePracticed = totalTimePracticed.toLongOrNull() ?: 0
                    )
                    viewModel.addPiece(piece)
                    onPieceAdded() // Callback to navigate back
                },
                enabled = name.isNotBlank() && composer.isNotBlank()
            ) {
                Text("Add Piece")
            }
        }
}
