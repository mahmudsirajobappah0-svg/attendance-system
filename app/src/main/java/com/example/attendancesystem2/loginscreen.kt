package com.example.attendancesystem2

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

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
                    colors = listOf(Color(0xFF111A2A), background, Color(0xFF080C14))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "ATTENDANCE", color = gold, fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = "Smart Attendance System", color = gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor, RoundedCornerShape(28.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(28.dp))
                    .padding(24.dp)
            ) {
                Text(text = "Welcome Back", color = white, fontSize = 26.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Login to continue", color = gray, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(22.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; message = "" },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    placeholder = { Text("example@gmail.com") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = gold,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                        focusedContainerColor = fieldColor,
                        unfocusedContainerColor = fieldColor,
                        focusedTextColor = white,
                        unfocusedTextColor = white,
                        cursorColor = gold,
                        focusedLabelColor = gold,
                        unfocusedLabelColor = gray,
                        focusedPlaceholderColor = gray,
                        unfocusedPlaceholderColor = gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; message = "" },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    placeholder = { Text("Enter your password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = gold,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                        focusedContainerColor = fieldColor,
                        unfocusedContainerColor = fieldColor,
                        focusedTextColor = white,
                        unfocusedTextColor = white,
                        cursorColor = gold,
                        focusedLabelColor = gold,
                        unfocusedLabelColor = gray,
                        focusedPlaceholderColor = gray,
                        unfocusedPlaceholderColor = gray
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
                        val cleanEmail = email.trim()

                        if (cleanEmail.isEmpty()) {
                            message = "Please enter your email"; isError = true; return@Button
                        }
                        if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
                            message = "Please enter a valid email address"; isError = true; return@Button
                        }
                        if (password.isEmpty()) {
                            message = "Please enter your password"; isError = true; return@Button
                        }

                        loading = true
                        message = ""

                        auth.signInWithEmailAndPassword(cleanEmail, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val uid = auth.currentUser?.uid ?: ""

                                    UserRepository.getProfile(
                                        uid = uid,
                                        onSuccess = { profile ->
                                            loading = false
                                            isError = false
                                            message = "Login successful!"
                                            onLoginSuccess(profile.role)
                                        },
                                        onFailure = { error ->
                                            loading = false
                                            isError = true
                                            message = "Could not load your profile: $error"
                                        }
                                    )
                                } else {
                                    loading = false
                                    isError = true
                                    message = "LOGIN FAILED: " +
                                        (task.exception?.localizedMessage ?: "Unknown error")
                                }
                            }
                    },
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth().height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = gold, contentColor = Color(0xFF101722))
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp),
                            color = Color(0xFF101722),
                            strokeWidth = 3.dp
                        )
                    } else {
                        Text(text = "Login", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Don't have an account? ", color = gray)
                    Text(
                        text = "Register",
                        color = gold,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onRegisterClick() }
                    )
                }
            }
        }
    }
}
