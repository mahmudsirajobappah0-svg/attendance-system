package com.example.attendancesystem2


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StudentDashboard() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("Student Dashboard")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {}) {
            Text("Scan Attendance")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {}) {
            Text("Attendance History")
        }
    }
}
