package com.example.attendancesystem2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LecturerBackground = Color(0xFF080D17)
private val LecturerCard = Color(0xFF111927)
private val LecturerGold = Color(0xFFE7B96B)
private val LecturerWhite = Color(0xFFF5F5F5)
private val LecturerGray = Color(0xFF9BA5B5)

@Composable
fun LecturerDashboard(
    onLogout: () -> Unit,
    onCreateAttendance: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF111A2A),
                        LecturerBackground,
                        Color(0xFF080C14)
                    )
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Welcome back 👋",
            color = LecturerGray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Lecturer Dashboard",
            color = LecturerWhite,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(35.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = LecturerCard
            )
        ) {

            Column(
                modifier = Modifier.padding(22.dp)
            ) {

                Text(
                    text = "Attendance Management",
                    color = LecturerWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Create a new attendance session and allow students to scan the QR code.",
                    color = LecturerGray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = onCreateAttendance,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LecturerGold,
                contentColor = Color(0xFF101722)
            )
        ) {

            Text(
                text = "Create Attendance QR",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
      Spacer(modifier = Modifier.height(15.dp))

OutlinedButton(
    onClick = onOpenAdmin,
    modifier = Modifier
        .fillMaxWidth()
        .height(55.dp),
    shape = RoundedCornerShape(18.dp),
    colors = ButtonDefaults.outlinedButtonColors(
        contentColor = LecturerGold
    )
) {
    Text(
        text = "Reset Student Device",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )
}
        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = LecturerGold
            )
        ) {

            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
