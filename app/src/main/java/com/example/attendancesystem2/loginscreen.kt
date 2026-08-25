package com.example.attendancesystem2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF080D17)
private val CardColor = Color(0xFF111927)
private val FieldColor = Color(0xFF151E2D)
private val Gold = Color(0xFFE7B96B)
private val GoldLight = Color(0xFFFFDFA3)
private val TextWhite = Color(0xFFF5F5F5)
private val TextGray = Color(0xFF9BA5B5)

@Composable
fun LoginScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF111A2A),
                        Background,
                        Color(0xFF080C14)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // Logo
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF182437),
                                Color(0xFF0E1522)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = Gold.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(28.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = GoldLight,
                    fontSize = 46.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // App title
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Attendance",
                    color = TextWhite,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = "System",
                    color = Gold,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Smart. Secure. Seamless.",
                color = TextGray,
                fontSize = 15.sp,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Login card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(CardColor.copy(alpha = 0.92f))
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(24.dp)
            ) {

                Text(
                    text = "Welcome back",
                    color = TextWhite,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Sign in to continue",
                    color = TextGray,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(26.dp))

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = {
                        Text("Email")
                    },
                    placeholder = {
                        Text("Enter your email")
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Gold,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.10f),
                        focusedContainerColor = FieldColor,
                        unfocusedContainerColor = FieldColor,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedLabelColor = Gold,
                        unfocusedLabelColor = TextGray,
                        focusedPlaceholderColor = TextGray,
                        unfocusedPlaceholderColor = TextGray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    label = {
                        Text("Password")
                    },
                    placeholder = {
                        Text("Enter your password")
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Gold,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.10f),
                        focusedContainerColor = FieldColor,
                        unfocusedContainerColor = FieldColor,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedLabelColor = Gold,
                        unfocusedLabelColor = TextGray,
                        focusedPlaceholderColor = TextGray,
                        unfocusedPlaceholderColor = TextGray
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Forgot password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Forgot password?",
                        color = Gold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Login button
                Button(
                    onClick = {
                        // Login functionality will be added later
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gold,
                        contentColor = Color(0xFF101722)
                    )
                ) {
                    Text(
                        text = "Login  →",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Divider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.12f))
                    )

                    Text(
                        text = "  or continue with  ",
                        color = TextGray,
                        fontSize = 13.sp
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.12f))
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Social buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    SocialButton(
                        text = "G",
                        modifier = Modifier.weight(1f)
                    )

                    SocialButton(
                        text = "",
                        modifier = Modifier.weight(1f)
                    )

                    SocialButton(
                        text = "M",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sign up
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(FieldColor)
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row {
                        Text(
                            text = "Don't have an account? ",
                            color = TextGray,
                            fontSize = 14.sp
                        )

                        Text(
                            text = "Sign up",
                            color = Gold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Security section
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Gold.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        color = Gold,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Your data is protected",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "Secure attendance management",
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
private fun SocialButton(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(58.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(FieldColor)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TextWhite,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
