package com.example.test2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

val sapPrimary = Color(0xFF0A6ED1)
val sapBackground = Color(0xFFF5F6F6)
val sapAssistantBg = Color(0xFFE8F4FF)
val sapUserText = Color(0xFF1A2742)

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(sapBackground)
    ) {
        // **SAP Joule Header**
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(sapPrimary),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sap_logo),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Bot",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Beta",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
            }
        }

        // **Chat Messages**
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn {
                items(messages) { message ->
                    ChatMessageItem(message = message)
                }
                if (isTyping) {
                    item { SAPTypingIndicator() }
                }
            }
        }

        // **Input Area**
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.White)
                    .padding(horizontal = 12.dp)
            ) {
                OutlinedTextField(
                    value = currentMessage,
                    onValueChange = { currentMessage = it },
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 48.dp),
                    placeholder = { Text("Ask me anything...") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (currentMessage.isNotEmpty()) {
                                    messages = messages + ChatMessage(currentMessage, true)
                                    currentMessage = ""
                                    isTyping = true
                                }
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = sapPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        if (currentMessage.isNotEmpty()) {
                        messages = messages + ChatMessage(currentMessage, true)
                        currentMessage = ""
                        isTyping = true
                    } })
                )
            }
        }
    }

    // **Simulate AI Response**
    LaunchedEffect(isTyping) {
        if (isTyping) {
            delay(1500)
            isTyping = false
            messages = messages + ChatMessage(
                    "hey Anshu!!",
                false
            )
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .background(
                    color = if (message.isUser) Color.White else sapAssistantBg,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (message.isUser) Color(0xFFE0E0E0) else Color(0xFFC4DBF5),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            if (!message.isUser) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_joule),
                        contentDescription = "Joule",
                        tint = sapPrimary,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Bot",
                        color = sapPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = message.text,
                color = if (message.isUser) sapUserText else Color(0xFF2B3A4D),
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun SAPTypingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_joule),
            contentDescription = "Typing",
            tint = sapPrimary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        repeat(3) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(sapPrimary, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

data class ChatMessage(val text: String, val isUser: Boolean)
