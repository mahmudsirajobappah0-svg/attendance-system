package com.example.attendancesystem2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LecturerDashboard(

    onLogout: () -> Unit
) {

    val background = Color(0xFF080D17)
    val card = Color(0xFF111927)
    val gold = Color(0xFFE7B96B)
    val white = Color(0xFFF5F5F5)
    val gray = Color(0xFF9BA5B5)

    Column(

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
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Welcome 👋",
            color = gray
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Lecturer Dashboard",
            color = white,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(
                containerColor = card
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Today's Class",
                    color = gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "No Active Session",
                    color = gold,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text =
                        "Create an attendance session for your students",
                    color = gray
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(

            onClick = {
                // QR generation will be added here
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),

            shape = RoundedCornerShape(18.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = gold,
                contentColor = Color.Black
            )
        ) {

            Text(
                text = "Generate Attendance QR",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedButton(

            onClick = onLogout,

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {

            Text(
                text = "Logout",
                color = gold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Attendance Records",
            color = white,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "No attendance sessions yet",
            color = gray
        )
    }
}
