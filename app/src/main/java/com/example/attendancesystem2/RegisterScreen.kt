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

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val background = Color(0xFF080D17)
    val cardColor = Color(0xFF111927)
    val fieldColor = Color(0xFF151E2D)
    val gold = Color(0xFFE7B96B)
    val white = Color(0xFFF5F5F5)
    val gray = Color(0xFF9BA5B5)

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

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Create Account",
                color = white,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Join the smarter way to manage attendance",
                color = gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        cardColor,
                        RoundedCornerShape(28.dp)
                    )
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.08f),
                        RoundedCornerShape(28.dp)
                    )
                    .padding(24.dp)
            ) {

                Text(
                    text = "Your Details",
                    color = white,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Full Name") },
                    placeholder = { Text("Enter your full name") },
                    shape = RoundedCornerShape(18.dp),
                    colors = registerFieldColors(
                        gold,
                        fieldColor,
                        white,
                        gray
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Email") },
                    placeholder = { Text("Enter your email") },
                    shape = RoundedCornerShape(18.dp),
                    colors = registerFieldColors(
                        gold,
                        fieldColor,
                        white,
                        gray
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation =
                        PasswordVisualTransformation(),
                    label = { Text("Password") },
                    placeholder = {
                        Text("Create a password")
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = registerFieldColors(
                        gold,
                        fieldColor,
                        white,
                        gray
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation =
                        PasswordVisualTransformation(),
                    label = { Text("Confirm Password") },
                    placeholder = {
                        Text("Repeat your password")
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = registerFieldColors(
                        gold,
                        fieldColor,
                        white,
                        gray
                    )
                )

                if (message.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = message,
                        color = gold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {

                        when {

                            name.isBlank() ||
                            email.isBlank() ||
                            password.isBlank() ||
                            confirmPassword.isBlank() -> {

                                message =
                                    "Please fill in all fields"
                            }

                            password != confirmPassword -> {

                                message =
                                    "Passwords do not match"
                            }

                            password.length < 6 -> {

                                message =
                                    "Password must be at least 6 characters"
                            }

                            else -> {

                                message = ""

                                // Firebase registration
                                // will be connected next

                                onRegisterSuccess()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = gold,
                        contentColor = Color(0xFF101722)
                    )
                ) {

                    Text(
                        text = "Create Account  →",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.Center
                ) {

                    Text(
                        text = "Already have an account? ",
                        color = gray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Login",
                        color = gold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onBackToLogin()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "🔒 Your information is securely protected",
                color = gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun registerFieldColors(
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
