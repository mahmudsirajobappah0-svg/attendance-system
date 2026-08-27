package com.example.attendancesystem2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

private val Background = Color(0xFF080D17)
private val Card = Color(0xFF111927)
private val Field = Color(0xFF151E2D)
private val Gold = Color(0xFFE7B96B)
private val White = Color(0xFFF5F5F5)
private val Gray = Color(0xFF9BA5B5)

@Composable
fun StudentDashboard(
    onLogout: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF111A2A),
                        Background
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = "Good morning 👋",
                        color = Gray,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Student",
                        color = White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            Gold.copy(alpha = 0.15f),
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            1.dp,
                            Gold.copy(alpha = 0.4f),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "S",
                        color = Gold,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Attendance overview
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF192437),
                                Card
                            )
                        ),
                        RoundedCornerShape(26.dp)
                    )
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.08f),
                        RoundedCornerShape(26.dp)
                    )
                    .padding(24.dp)
            ) {

                Text(
                    text = "Attendance overview",
                    color = Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = "87%",
                            color = Gold,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Overall attendance",
                            color = White,
                            fontSize = 14.sp
                        )
                    }

                    Text(
                        text = "Excellent",
                        color = Gold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            Field,
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.87f)
                            .fillMaxHeight()
                            .background(
                                Gold,
                                RoundedCornerShape(10.dp)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Scan QR button
            Button(
                onClick = {
                    // QR scanner will be connected next
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Color(0xFF101722)
                )
            ) {

                Text(
                    text = "▣",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Scan Attendance QR",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Today's attendance",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Today's attendance card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Card,
                        RoundedCornerShape(20.dp)
                    )
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.07f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            Gold.copy(alpha = 0.12f),
                            RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        color = Gold,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Today's status",
                        color = White,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "No attendance recorded",
                        color = Gray,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = "Pending",
                    color = Gold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Quick access",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                DashboardButton(
                    title = "History",
                    symbol = "◷",
                    modifier = Modifier.weight(1f)
                )

                DashboardButton(
                    title = "Profile",
                    symbol = "●",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logout button
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B2E2E),
                    contentColor = White
                )
            ) {
                Text(
                    text = "Logout",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun DashboardButton(
    title: String,
    symbol: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .height(105.dp)
            .background(
                Field,
                RoundedCornerShape(20.dp)
            )
            .border(
                1.dp,
                Color.White.copy(alpha = 0.07f),
                RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = symbol,
            color = Gold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
