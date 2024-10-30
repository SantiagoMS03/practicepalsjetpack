package com.zybooks.practicepals.ui.pieces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.zybooks.practicepals.viewmodel.PieceViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PieceListScreen(
    viewModel: PieceViewModel = hiltViewModel(),
    onAddPieceClick: () -> Unit,
    onPieceClick: (Int) -> Unit)
{
    val pieces by viewModel.piecesFlow.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPieceClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Piece")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(pieces) { piece ->
                ListItem(
                    headlineContent = { Text(piece.name) },
                    supportingContent = { Text(piece.composer)},
                    modifier = Modifier.clickable() { onPieceClick(piece.pieceId) }
                )
            }
        }
    }
}

