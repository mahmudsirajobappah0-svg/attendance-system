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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("student") }

    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val background = Color(0xFF080D17)
    val cardColor = Color(0xFF111927)
    val fieldColor = Color(0xFF151E2D)
    val gold = Color(0xFFE7B96B)
    val white = Color(0xFFF5F5F5)
    val gray = Color(0xFF9BA5B5)

    val auth = FirebaseAuth.getInstance()

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = gold,
        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
        focusedContainerColor = fieldColor,
        unfocusedContainerColor = fieldColor,
        focusedTextColor = white,
        unfocusedTextColor = white,
        cursorColor = gold,
        focusedLabelColor = gold,
        unfocusedLabelColor = gray
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF111A2A), background)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(text = "Create Account", color = white, fontSize = 30.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Create your attendance account", color = gray)

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor, RoundedCornerShape(28.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(28.dp))
                    .padding(24.dp)
            ) {
                Text(text = "I am a", color = white, fontSize = 15.sp)

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { selectedRole = "student" },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedRole == "student") gold else fieldColor,
                            contentColor = if (selectedRole == "student") Color.Black else white
                        )
                    ) { Text("Student") }

                    Button(
                        onClick = { selectedRole = "lecturer" },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedRole == "lecturer") gold else fieldColor,
                            contentColor = if (selectedRole == "lecturer") Color.Black else white
                        )
                    ) { Text("Lecturer") }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Full Name") },
                    singleLine = true,
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    singleLine = true,
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    colors = fieldColors
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (message.isNotEmpty()) {
                    Text(text = message, color = Color(0xFFFF6B6B))
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Button(
                    onClick = {
                        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                            message = "Please fill all fields"
                            return@Button
                        }
                        if (password != confirmPassword) {
                            message = "Passwords do not match"
                            return@Button
                        }

                        loading = true
                        message = ""

                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val uid = auth.currentUser?.uid ?: ""
                                    val deviceId = DeviceUtils.getDeviceId(context)
com.google.firebase.messaging.FirebaseMessaging.getInstance().token
    .addOnCompleteListener { tokenTask ->
        val fcmToken = if (tokenTask.isSuccessful) tokenTask.result else ""

        UserRepository.createProfile(
            profile = UserProfile(
                uid = uid,
                name = name.trim(),
                email = email.trim(),
                role = selectedRole,
                deviceId = deviceId,
                fcmToken = fcmToken
            ),
            onSuccess = {
                loading = false
                onRegisterSuccess()
            },
            onFailure = { error ->
                loading = false
                message = "Account created, but profile setup failed: $error"
            }
        )
    }
                                    UserRepository.createProfile(
                                        profile = UserProfile(
                                            uid = uid,
                                            name = name.trim(),
                                            email = email.trim(),
                                            role = selectedRole,
                                            deviceId = deviceId
                                        ),
                                        onSuccess = {
                                            loading = false
                                            onRegisterSuccess()
                                        },
                                        onFailure = { error ->
                                            loading = false
                                            message = "Account created, but profile setup failed: $error"
                                        }
                                    )
                                } else {
                                    loading = false
                                    message = task.exception?.message ?: "Registration failed"
                                }
                            }
                    },
                    modifier = Modifier.fillMaxWidth().height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = gold, contentColor = Color.Black)
                ) {
                    if (loading) {
                        CircularProgressIndicator(modifier = Modifier.size(25.dp))
                    } else {
                        Text(text = "Create Account", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Already have an account? Login",
                    color = gold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onBackToLogin() }
                )
            }
        }
    }
}
