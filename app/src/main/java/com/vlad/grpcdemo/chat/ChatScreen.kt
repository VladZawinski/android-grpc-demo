package com.vlad.grpcdemo.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vlad.grpcdemo.chat.model.MessageModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = koinViewModel(),
) {
    val messageFlow by viewModel.messageFlow.collectAsState()
    LaunchedEffect(key1 = messageFlow) {
        Timber.d("$messageFlow")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

    }
}


@Composable
fun MessageBubble(message: MessageModel) {
    val backgroundColor = if (message.isSender) Color.LightGray else Color.White
    val alignment = if (message.isSender) Alignment.End else Alignment.Start

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isSender) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = message.body)
        }
    }
}