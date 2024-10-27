package com.zybooks.practicepals.ui.pieces

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.zybooks.practicepals.viewmodel.PieceViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PieceDetailScreen(pieceId: Int, viewModel: PieceViewModel = viewModel()) {
    val pieceFlow = remember(pieceId) { viewModel.getPieceDetails(pieceId) }
    val piece by pieceFlow.collectAsState(initial = null)

    piece?.let {
        // Display the details of the piece
        Text(text = "Piece Name: ${it.name}")
        Text(text = "Composer: ${it.composer}")
        Text(text = "Total Time Practiced: ${it.totalTimePracticed}")
        // Add more details as needed
    } ?: run {
        // Show a loading indicator or an error message
        Text(text = "Loading...")
    }
}
