package com.example.attendancesystem2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val background = Color(0xFF080D17)
    val cardColor = Color(0xFF111927)
    val fieldColor = Color(0xFF151E2D)
    val gold = Color(0xFFE7B96B)
    val white = Color(0xFFF5F5F5)
    val gray = Color(0xFF9BA5B5)

    val auth = FirebaseAuth.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF111A2A),
                        background,
                        Color(0xFF080C14)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "ATTENDANCE",
                color = gold,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Digital Attendance System",
                color = gray,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(45.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        cardColor,
                        RoundedCornerShape(28.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(24.dp)
            ) {

                Text(
                    text = "Welcome Back 👋",
                    color = white,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Login to continue",
                    color = gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = {
                        Text("Email")
                    },
                    placeholder = {
                        Text("Enter your email")
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = loginFieldColors(
                        gold,
                        fieldColor,
                        white,
                        gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = {
                        Text("Password")
                    },
                    placeholder = {
                        Text("Enter your password")
                    },
                    visualTransformation =
                        PasswordVisualTransformation(),
                    shape = RoundedCornerShape(18.dp),
                    colors = loginFieldColors(
                        gold,
                        fieldColor,
                        white,
                        gray
                    )
                )

                if (errorMessage.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {

                        if (email.isBlank() || password.isBlank()) {

                            errorMessage =
                                "Please enter your email and password"

                            return@Button
                        }

                        isLoading = true

                        auth.signInWithEmailAndPassword(
                            email.trim(),
                            password
                        ).addOnCompleteListener { task ->

                            isLoading = false

                            if (task.isSuccessful) {

                                onLogin()

                            } else {

                                errorMessage =
                                    task.exception?.message
                                        ?: "Login failed. Please try again."
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    enabled = !isLoading,
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = gold,
                        contentColor = Color(0xFF101722)
                    )
                ) {

                    if (isLoading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color(0xFF101722),
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            text = "Login →",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Don't have an account? ",
                        color = gray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Register",
                        color = gold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onRegister()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "🔒 Secure Digital Attendance System",
                color = gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun loginFieldColors(
    gold: Color,
    fieldColor: Color,
    white: Color,
    gray: Color
) = OutlinedTextFieldDefaults.colors(

    focusedBorderColor = gold,

    unfocusedBorderColor =
        Color.White.copy(alpha = 0.10f),

    focusedContainerColor = fieldColor,

    unfocusedContainerColor = fieldColor,

    focusedTextColor = white,

    unfocusedTextColor = white,

    focusedLabelColor = gold,

    unfocusedLabelColor = gray,

    focusedPlaceholderColor = gray,

    unfocusedPlaceholderColor = gray
)
