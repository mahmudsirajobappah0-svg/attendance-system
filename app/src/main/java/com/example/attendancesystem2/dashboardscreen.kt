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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val DashboardBackground = Color(0xFF080D17)
private val DashboardCard = Color(0xFF111927)
private val DashboardGold = Color(0xFFE7B96B)
private val DashboardText = Color(0xFFF5F5F5)
private val DashboardGray = Color(0xFF9BA5B5)


@Composable
fun StudentDashboard(
    onLogout: () -> Unit,
    onScanAttendance: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF111A2A),
                        DashboardBackground,
                        Color(0xFF080C14)
                    )
                )
            )
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        // Welcome text
        Text(
            text = "Welcome back 👋",
            color = DashboardGray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Student Dashboard",
            color = DashboardText,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))


        // Attendance Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = DashboardCard
            )
        ) {

            Column(
                modifier = Modifier.padding(22.dp)
            ) {

                Text(
                    text = "Today's Attendance",
                    color = DashboardGray,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Not Marked",
                    color = DashboardGold,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Scan your lecturer's QR code to mark attendance",
                    color = DashboardGray,
                    fontSize = 13.sp
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))


        // Statistics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            DashboardStat(
                title = "Attendance",
                value = "0%",
                modifier = Modifier.weight(1f)
            )

            DashboardStat(
                title = "Classes",
                value = "0",
                modifier = Modifier.weight(1f)
            )
        }


        Spacer(modifier = Modifier.height(30.dp))


        // Scan Attendance Button
       Button(
    onClick = onScanAttendance,
           
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DashboardGold,
                contentColor = Color(0xFF101722)
            )
        ) {

            Text(
                text = "Scan Attendance 📷",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(16.dp))


        // Logout Button
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = DashboardGold
            )
        ) {

            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }


        Spacer(modifier = Modifier.height(30.dp))


        // Recent Activity
        Text(
            text = "Recent Activity",
            color = DashboardText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(DashboardCard)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "No attendance records yet",
                color = DashboardGray,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun DashboardStat(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(DashboardCard)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.06f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(18.dp)
    ) {

        Text(
            text = value,
            color = DashboardGold,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = title,
            color = DashboardGray,
            fontSize = 13.sp
        )
    }
}
