package com.example.attendancesystem2

import androidx.compose.foundation.background
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

private val StudentBackground = Color(0xFF080D17)
private val StudentCard = Color(0xFF111927)
private val StudentGold = Color(0xFFE7B96B)
private val StudentWhite = Color(0xFFF5F5F5)
private val StudentGray = Color(0xFF9BA5B5)

@Composable
fun StudentDashboard(
    onLogout: () -> Unit,
    onScanAttendance: () -> Unit,
    onViewHistory: () -> Unit
) {
    val email = FirebaseAuth.getInstance().currentUser?.email ?: "Student"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF111A2A), StudentBackground, Color(0xFF080C14))
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))

        Text(text = "Welcome back 👋", color = StudentGray, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(6.dp))

        Text(text = email, color = StudentWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(35.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = StudentCard)
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Text(text = "Mark Your Attendance", color = StudentWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Scan the QR code shown by your lecturer to mark attendance for this class.",
                    color = StudentGray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = onScanAttendance,
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = StudentGold, contentColor = Color(0xFF101722))
        ) {
            Text(text = "Scan Attendance QR", fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedButton(
            onClick = onViewHistory,
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = StudentGold)
        ) {
            Text(text = "View Attendance History", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = StudentGold)
        ) {
            Text(text = "Logout", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
