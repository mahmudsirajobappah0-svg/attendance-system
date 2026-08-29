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
fun RegisterScreen(

    onRegisterSuccess: () -> Unit,

    onBackToLogin: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

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
                        background
                    )
                )
            )
    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Create Account",
                color = white,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Create your attendance account",
                color = gray
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

                OutlinedTextField(

                    value = name,

                    onValueChange = {
                        name = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Full Name")
                    },

                    singleLine = true
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(

                    value = email,

                    onValueChange = {
                        email = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Email")
                    },

                    singleLine = true
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(

                    value = password,

                    onValueChange = {
                        password = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Password")
                    },

                    visualTransformation =
                        PasswordVisualTransformation(),

                    singleLine = true
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(

                    value = confirmPassword,

                    onValueChange = {
                        confirmPassword = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Confirm Password")
                    },

                    visualTransformation =
                        PasswordVisualTransformation(),

                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (message.isNotEmpty()) {

                    Text(
                        text = message,
                        color = Color.Red
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                Button(

                    onClick = {

                        if (
                            name.isEmpty() ||
                            email.isEmpty() ||
                            password.isEmpty()
                        ) {

                            message =
                                "Please fill all fields"

                            return@Button
                        }

                        if (password != confirmPassword) {

                            message =
                                "Passwords do not match"

                            return@Button
                        }

                        loading = true
                        message = ""

                        auth
                            .createUserWithEmailAndPassword(
                                email,
                                password
                            )
                            .addOnCompleteListener { task ->

                                loading = false

                                if (task.isSuccessful) {

                                    onRegisterSuccess()

                                } else {

                                    message =
                                        task.exception?.message
                                            ?: "Registration failed"
                                }
                            }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = gold,
                        contentColor = Color.Black
                    )
                ) {

                    if (loading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(25.dp)
                        )

                    } else {

                        Text(
                            text = "Create Account",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Already have an account? Login",
                    color = gold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            onBackToLogin()
                        }
                )
            }
        }
    }
}
