package com.zybooks.practicepals.ui.pieces


import android.widget.ImageButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zybooks.practicepals.database.entities.Piece
import com.zybooks.practicepals.viewmodel.PieceViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zybooks.practicepals.ui.navigation.LocalNavController
import com.zybooks.practicepals.utilities.toFormattedDate
import com.zybooks.practicepals.utilities.toFormattedTime
import com.zybooks.practicepals.viewmodel.PracticeLogViewModel

@Composable
fun EditPieceScreen (
    pieceId: Int,
    pieceViewModel: PieceViewModel = hiltViewModel(),
    practiceLogViewModel: PracticeLogViewModel = hiltViewModel()
) {
    val pieceFlow = remember(pieceId) { pieceViewModel.getPieceDetails(pieceId) }
    val piece by pieceFlow.collectAsState(initial = null)

    val practiceLogs by practiceLogViewModel.getPracticeLogsForPiece(pieceId).collectAsState(initial = emptyList())

    val navController = LocalNavController.current // Access NavController without passing as a parameter

    piece?.let {
        var name by remember { mutableStateOf(it.name) }
        var composer by remember { mutableStateOf(it.composer) }

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
            Text(
                text = "Total Time Practiced: ${it.totalTimePracticed.toFormattedTime()}",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (practiceLogs.isEmpty()) {
                Column {
                    Text(
                        text = "Practice",
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            navController?.navigate("stopwatch")
                        }
                    )
                    Text(text = " so you can visualize your practice logs here!")
                }
            } else {
                Text(
                    text = "Practice Logs:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyColumn {
                    items(practiceLogs) { practiceLog ->
                        ListItem(
                            headlineContent = { Text(practiceLog.practiceLogDescription) },
                            supportingContent = @Composable {
                                Column {
                                    Text("Logged on: ${practiceLog.dateLogged.toFormattedDate()}")
                                    Text("Duration: ${practiceLog.timeLogged.toFormattedTime()}")
                                    IconButton(onClick = { practiceLogViewModel.removePracticeLog(practiceLog) }) {
                                        androidx.compose.material3.Icon(painterResource(id = com.zybooks.practicepals.R.drawable.ic_trash), contentDescription = "Decrease BPM")
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .clickable { /* Handle item click if needed */ }
                        )
                    }
                }
            }
        }
    } ?: run {
        Text(text = "Loading...", modifier = Modifier.padding(16.dp))
    }
}
