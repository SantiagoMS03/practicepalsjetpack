package com.zybooks.practicepals.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
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

        // Add more buttons here for other screens
    }
}
