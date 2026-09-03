package com.example.attendancesystem2

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue 
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= 33) {
    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
    }
}
        setContent {
            var currentScreen by remember { mutableStateOf("login") }

            when (currentScreen) {
                "login" -> {
                    LoginScreen(
                        onLoginSuccess = { role ->
                            currentScreen = if (role == "lecturer") "lecturerDashboard" else "studentDashboard"
                        },
                        onRegisterClick = { currentScreen = "register" }
                    )
                }

                "register" -> {
                    RegisterScreen(
                        onRegisterSuccess = { currentScreen = "login" },
                        onBackToLogin = { currentScreen = "login" }
                    )
                }

                "studentDashboard" -> {
                    StudentDashboard(
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            currentScreen = "login"
                        },
                        onScanAttendance = { currentScreen = "scanner" },
                        onViewHistory = { currentScreen = "history" }
                    )
                }

                "scanner" -> {
                    QrScannerScreen(
                        onBack = { currentScreen = "studentDashboard" },
                        onAttendanceMarked = { currentScreen = "studentDashboard" }
                    )
                }

                "history" -> {
                    AttendanceHistoryScreen(onBack = { currentScreen = "studentDashboard" })
                }

              "lecturerDashboard" -> {
    LecturerDashboard(
        onLogout = {
            FirebaseAuth.getInstance().signOut()
            currentScreen = "login"
        },
        onCreateAttendance = { currentScreen = "createAttendance" },
        onOpenAdmin = { currentScreen = "admin" }
    )
}

"admin" -> {
    AdminScreen(
        onBack = { currentScreen = "lecturerDashboard" }
    )
}
                "createAttendance" -> {
                    CreateAttendanceScreen(onBack = { currentScreen = "lecturerDashboard" })
                }
            }
        }
    }
}
