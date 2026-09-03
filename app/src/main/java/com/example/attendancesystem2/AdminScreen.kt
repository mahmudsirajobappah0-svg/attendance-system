package com.example.attendancesystem2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF080D17)
private val FieldColor = Color(0xFF151E2D)
private val Gold = Color(0xFFE7B96B)
private val White = Color(0xFFF5F5F5)
private val Gray = Color(0xFF9BA5B5)

@Composable
fun AdminScreen(onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(Background).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Reset Student Device", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Unlinks a student's account from their current device — use this if they lost or replaced their phone.",
            color = Gray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Student's email") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gold,
                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                focusedContainerColor = FieldColor,
                unfocusedContainerColor = FieldColor,
                focusedTextColor = White,
                unfocusedTextColor = White,
                cursorColor = Gold,
                focusedLabelColor = Gold,
                unfocusedLabelColor = Gray
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (isError) Color(0xFFFF6B6B) else Color(0xFF66E08A),
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(
            onClick = {
                if (email.isBlank()) {
                    message = "Enter the student's email"; isError = true
                    return@Button
                }
                loading = true
                FirestoreRepository.resetDeviceByEmail(
                    email = email,
                    onSuccess = {
                        loading = false; isError = false
                        message = "Device reset. The student can now log in from a new device."
                    },
                    onFailure = { error ->
                        loading = false; isError = true
                        message = error
                    }
                )
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color.Black)
        ) {
            if (loading) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black, strokeWidth = 3.dp)
            else Text("Reset Device", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) {
            Text(text = "Back", color = Gold)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
