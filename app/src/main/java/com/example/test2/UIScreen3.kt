package com.example.test2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class UIScreen3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatScreenContent()
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    UserSelectionCard("Anshu", "Developer", "anshu@example.com")
}

@Composable
fun ChatScreenContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        ChatHeader()
        Spacer(modifier = Modifier.height(16.dp)) // Adds spacing between header and message
        ChatMessageItem(UserMessage("Hello! How can I help you?", isUser = false))
    }
}

@Composable
fun ChatHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF6200EA), Color(0xFF9C27B0))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Joule",
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ChatMessageItem(message: UserMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        if (!message.isUser) {
            Text(
                text = "Joule",
                fontSize = 14.sp,
                color = Color(0xFF6200EA),
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
            )
        }

        Box(
            modifier = Modifier
                .widthIn(max = 325.dp)
                .background(
                    color = if (message.isUser) Color(0xFF6200EA) else Color(0xFFF1F1F1),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (message.isUser) Color(0xFF6200EA) else Color(0xFFDDDDDD),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) Color.White else Color.Black,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun UserSelectionCard(name: String, role: String, email: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(2.dp, shape = RoundedCornerShape(12.dp)), // Proper shadow placement
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 16.sp)
                Text(text = role, fontSize = 14.sp, color = Color.Gray)
                Text(text = email, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

data class UserMessage(val text: String, val isUser: Boolean)
