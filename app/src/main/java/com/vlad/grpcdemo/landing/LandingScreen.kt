package com.vlad.grpcdemo.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.vlad.grpcdemo.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LandingViewModel = koinViewModel(),
) {
    var userName by remember { mutableStateOf("") }
    val event by viewModel.events.collectAsState()
    LaunchedEffect(event) {
        when(event) {
            LandingViewModelEvent.GoToChatScreen -> {
                navController.navigate(Screen.Chat.route)
            }
            LandingViewModelEvent.Idle -> Unit
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome! Please enter your name",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("User Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                enabled = userName.isNotEmpty(),
                onClick = {
                    viewModel.next(userName)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Next")
            }
            Button(
                onClick = {
                    navController.navigate(Screen.Stock.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Go To Stock")
            }
        }
    }
}