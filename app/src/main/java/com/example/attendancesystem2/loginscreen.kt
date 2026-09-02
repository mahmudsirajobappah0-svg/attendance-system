package com.example.attendancesystem2

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
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

    var selectedRole by remember {
        mutableStateOf("student")
    }

    var loading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

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
                    colors = listOf(
                        Color(0xFF111A2A),
                        background,
                        Color(0xFF080C14)
                    )
                )
            )
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "ATTENDANCE",
                color = gold,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Smart Attendance System",
                color = gray,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(35.dp)
            )

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
                    text = "Welcome Back",
                    color = white,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Login to continue",
                    color = gray,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                Text(
                    text = "Login as",
                    color = white,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Button(
                        onClick = {
                            selectedRole = "student"
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (selectedRole == "student") {
                                    gold
                                } else {
                                    fieldColor
                                },

                            contentColor =
                                if (selectedRole == "student") {
                                    Color(0xFF101722)
                                } else {
                                    white
                                }
                        )
                    ) {

                        Text("Student")
                    }


                    Button(
                        onClick = {
                            selectedRole = "lecturer"
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (selectedRole == "lecturer") {
                                    gold
                                } else {
                                    fieldColor
                                },

                            contentColor =
                                if (selectedRole == "lecturer") {
                                    Color(0xFF101722)
                                } else {
                                    white
                                }
                        )
                    ) {

                        Text("Lecturer")
                    }
                }


                Spacer(
                    modifier = Modifier.height(20.dp)
                )


                // EMAIL FIELD

                OutlinedTextField(

                    value = email,

                    onValueChange = {

                        email = it
                        errorMessage = ""

                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Email")
                    },

                    placeholder = {
                        Text("example@gmail.com")
                    },

                    singleLine = true,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),

                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = gold,

                        unfocusedBorderColor =
                            Color.White.copy(alpha = 0.15f),

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


                Spacer(
                    modifier = Modifier.height(15.dp)
                )


                // PASSWORD FIELD

                OutlinedTextField(

                    value = password,

                    onValueChange = {

                        password = it
                        errorMessage = ""

                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Password")
                    },

                    placeholder = {
                        Text("Enter your password")
                    },

                    singleLine = true,

                    visualTransformation =
                        PasswordVisualTransformation(),

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),

                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = gold,

                        unfocusedBorderColor =
                            Color.White.copy(alpha = 0.15f),

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


                Spacer(
                    modifier = Modifier.height(15.dp)
                )


                // ERROR MESSAGE

                if (errorMessage.isNotEmpty()) {

                    Text(
                        text = errorMessage,
                        color = Color(0xFFFF6B6B),
                        fontSize = 13.sp
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }


                // LOGIN BUTTON

                Button(

                    onClick = {

                        val cleanEmail = email.trim()
                        val cleanPassword = password

                        if (cleanEmail.isEmpty()) {

                            errorMessage =
                                "Please enter your email"

                            return@Button
                        }


                        if (
                            !Patterns.EMAIL_ADDRESS
                                .matcher(cleanEmail)
                                .matches()
                        ) {

                            errorMessage =
                                "Please enter a valid email address"

                            return@Button
                        }


                        if (cleanPassword.isEmpty()) {

                            errorMessage =
                                "Please enter your password"

                            return@Button
                        }


                        loading = true
                        errorMessage = ""


                        auth
                            .signInWithEmailAndPassword(
                                cleanEmail,
                                cleanPassword
                            )

                            .addOnCompleteListener { task ->

                                loading = false

                                if (task.isSuccessful) {

                                    onLoginSuccess(selectedRole)

                                } else {

                                    errorMessage =
                                        task.exception?.localizedMessage
                                            ?: "Login failed"
                                }
                            }
                    },

                    enabled = !loading,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = gold,
                        contentColor = Color(0xFF101722)
                    )
                ) {

                    if (loading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp),
                            color = Color(0xFF101722),
                            strokeWidth = 3.dp
                        )

                    } else {

                        Text(
                            text = "Login",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


                Spacer(
                    modifier = Modifier.height(20.dp)
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Don't have an account? ",
                        color = gray
                    )

                    Text(
                        text = "Register",
                        color = gold,
                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.clickable {
                            onRegisterClick()
                        }
                    )
                }
            }
        }
    }
}
