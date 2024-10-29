package com.zybooks.practicepals.ui.pieces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.practicepals.viewmodel.PieceViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PieceDetailScreen(
    pieceId: Int,
    viewModel: PieceViewModel = hiltViewModel()
) {
    val pieceFlow = remember(pieceId) { viewModel.getPieceDetails(pieceId) }
    val piece by pieceFlow.collectAsState(initial = null)

    piece?.let {
        // Use a Column to arrange the Text components vertically
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Piece Name: ${it.name}", fontSize = 20.sp)
            Text(text = "Composer: ${it.composer}", fontSize = 16.sp)
            Text(text = "Total Time Practiced: ${it.totalTimePracticed} minutes", fontSize = 16.sp)
            // Add more details or actions as needed
        }
    } ?: run {
        // Show a loading indicator or an error message
        Text(text = "Loading...", modifier = Modifier.padding(16.dp))
    }
}
