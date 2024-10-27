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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.zybooks.practicepals.database.dao.PieceDao
import com.zybooks.practicepals.viewmodel.PieceViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PieceListScreen (viewModel: PieceViewModel = viewModel(), onUserClick: (Int) -> Unit) {
    val pieces by viewModel.piecesFlow.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                /* Navigate to Add User Screen */
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add User")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(pieces) { piece ->
                ListItem(
                    headlineContent = { Text(piece.name) },
                    supportingContent = { Text(piece.composer)},
                    leadingContent = { Text(piece.totalTimePracticed.toString())},
                    modifier = Modifier.clickable() { onUserClick(piece.pieceId) }
                )
            }
        }
    }
}