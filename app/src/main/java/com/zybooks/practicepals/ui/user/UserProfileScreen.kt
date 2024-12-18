package com.zybooks.practicepals.ui.user

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zybooks.practicepals.viewmodel.UserViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.zybooks.practicepals.ui.navigation.LocalNavController

@Composable
fun UserProfileScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.observeAsState()
    var isEditing by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf(currentUser?.username ?: "") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.updateProfilePicture(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture Section
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Change Profile Picture")
        }

        // Username Section
        if (isEditing) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Add username update functionality here
                    isEditing = false
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Save")
            }
        } else {
            Text(
                text = "Username: ${currentUser?.username}",
                style = MaterialTheme.typography.bodyLarge
            )

            TextButton(onClick = { isEditing = true }) {
                Text("Edit")
            }
        }

        // Email Display
        Text(
            text = "Email: ${currentUser?.email}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}