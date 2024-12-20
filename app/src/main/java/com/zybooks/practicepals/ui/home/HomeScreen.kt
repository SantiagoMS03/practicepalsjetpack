package com.zybooks.practicepals.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.zybooks.practicepals.viewmodel.AdminViewModel
import com.zybooks.practicepals.viewmodel.PieceViewModel
import com.zybooks.practicepals.viewmodel.UiStateViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    uiStateViewModel: UiStateViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        uiStateViewModel.setPracticeBarVisibility(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to PracticePals!")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("metronome") }) {
            Text("Go to Metronome")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("pieces") }) {
            Text("Go to Pieces")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("stopwatch") }) {
            Text("Go to Stopwatch")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("practice_bar_chart") }) { // New Button
            Text("View Practice Bar Chart")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("admin")} ) { // New Button
            Text("Admin")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("user_register")} ) { // New Button
            Text("User")
        }

        // Add more buttons here for other screens
    }
}
