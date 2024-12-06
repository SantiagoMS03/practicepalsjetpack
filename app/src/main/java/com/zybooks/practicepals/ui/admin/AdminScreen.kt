package com.zybooks.practicepals.ui.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zybooks.practicepals.viewmodel.AdminViewModel
import com.zybooks.practicepals.viewmodel.PieceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen (
    viewModel: AdminViewModel = hiltViewModel(),
) {
    Column {
        Button(onClick = { viewModel.clearDatabase() }) {
            Text(text = "Clear DB")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.seedDatabase() }) {
            Text(text = "Seed DB")
        }
    }

}