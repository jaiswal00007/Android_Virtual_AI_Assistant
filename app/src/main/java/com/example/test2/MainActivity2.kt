package com.example.test2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*




class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatScreen()
        }
    }
}




@Composable
fun ChatScreen() {
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var currentMessage by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Messages display
        Column(modifier = Modifier.weight(1f)) {
            messages.forEach { message ->
                ChatBubble(text = message.text, isUser = message.isUser)
            }
            if (isTyping) TypingIndicator()
        }

        // Input and send button with Paper plane icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text field for user input
            TextField(
                value = currentMessage,
                onValueChange = { currentMessage = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Type a message...") }
            )

            // Paper plane button
            IconButton(
                onClick = {
                    if (currentMessage.isNotEmpty()) {
                        // Add the user's message
                        messages = messages + ChatMessage(currentMessage, isUser = true)
                        currentMessage = ""
                        isTyping = true
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_paper_plane), // Replace with your own paper plane icon resource
                    contentDescription = "Send Message",
                    tint = Color.Blue,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }

    // LaunchedEffect for simulating the AI response after typing
    LaunchedEffect(isTyping) {
        if (isTyping) {
            delay(2000)
            isTyping = false
            messages = messages + ChatMessage("AI: REPLY", isUser = false)
        }
    }
}

@Composable
fun ChatBubble(text: String, isUser: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = text,
            modifier = Modifier
                .background(
                    if (isUser) Color.Green else Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TypingIndicator() {
    val dots = listOf(".", "..", "...")
    var dotIndex by remember { mutableStateOf(0) }

    LaunchedEffect(true) {
        while (true) {
            delay(500)
            dotIndex = (dotIndex + 1) % dots.size
        }
    }

    Text(
        text = "AI is typing" + dots[dotIndex],
        color = Color.Gray,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 8.dp)
    )
}

data class ChatMessage(val text: String, val isUser: Boolean)
