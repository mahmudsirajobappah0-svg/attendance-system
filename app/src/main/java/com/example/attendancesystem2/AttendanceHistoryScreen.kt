package com.example.attendancesystem2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

private val Background = Color(0xFF080D17)
private val CardColor = Color(0xFF111927)
private val Gold = Color(0xFFE7B96B)
private val White = Color(0xFFF5F5F5)
private val Gray = Color(0xFF9BA5B5)

@Composable
fun AttendanceHistoryScreen(onBack: () -> Unit) {
    val auth = FirebaseAuth.getInstance()

    var records by remember { mutableStateOf<List<AttendanceRecord>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val studentId = auth.currentUser?.uid ?: ""
        FirestoreRepository.getStudentHistory(
            studentId = studentId,
            onSuccess = { result -> records = result; loading = false },
            onFailure = { error -> errorMessage = error; loading = false }
        )
    }

    val percentage = if (records.isNotEmpty()) {
        (records.count { it.status == "Present" } * 100) / records.size
    } else 0

    Column(modifier = Modifier.fillMaxSize().background(Background).padding(24.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Attendance History", color = White, fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardColor)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "$percentage%", color = Gold, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                Text(text = "Overall Attendance", color = Gray, fontSize = 13.sp)
            }
        }

        if (!loading && records.isNotEmpty() && percentage < 75) {
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2A1515))
            ) {
                Text(
                    text = "⚠ Your attendance is below 75%. Keep it up to avoid falling below the required minimum.",
                    color = Color(0xFFFF6B6B),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.weight(1f)) {
            when {
                loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Gold)
                    }
                }
                errorMessage.isNotEmpty() -> {
                    Text(text = errorMessage, color = Color(0xFFFF6B6B))
                }
                records.isEmpty() -> {
                    Text(text = "No attendance records yet", color = Gray, modifier = Modifier.padding(top = 20.dp))
                }
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(records) { record ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = CardColor)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = record.course, color = White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        Text(text = record.date, color = Gray, fontSize = 13.sp)
                                    }
                                    Text(text = record.status, color = Color(0xFF66E08A), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) {
            Text(text = "Back", color = Gold)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
