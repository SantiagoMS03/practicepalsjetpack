// PieceDetailScreen.kt
package com.zybooks.practicepals.ui.pieces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.practicepals.viewmodel.PieceViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.zybooks.practicepals.ui.navigation.LocalNavController
import com.zybooks.practicepals.viewmodel.PracticeLogViewModel
import com.zybooks.practicepals.utilities.toFormattedTime
import com.zybooks.practicepals.utilities.toFormattedDate

@Composable
fun PieceDetailScreen(
    pieceId: Int,
    pieceViewModel: PieceViewModel = hiltViewModel(),
    practiceLogViewModel: PracticeLogViewModel = hiltViewModel()
) {
    val pieceFlow = remember(pieceId) { pieceViewModel.getPieceDetails(pieceId) }
    val piece by pieceFlow.collectAsState(initial = null)

    val practiceLogs by practiceLogViewModel.getPracticeLogsForPiece(pieceId).collectAsState(initial = emptyList())

    val navController = LocalNavController.current // Access NavController without passing as a parameter

    piece?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Piece Name: ${it.name}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Composer: ${it.composer}",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Total Time Practiced: ${it.totalTimePracticed.toFormattedTime()}",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
//            Text(
//                text = "Edit",
//                fontSize = 16.sp,
//                modifier = Modifier.clickable()
//            )

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
