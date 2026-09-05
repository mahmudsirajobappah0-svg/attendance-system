package com.example.attendancesystem2

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        setContent {
            val context = LocalContext.current
            var currentScreen by remember { mutableStateOf("checking") }

            LaunchedEffect(Unit) {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    currentScreen = "login"
                } else {
                    val deviceId = DeviceUtils.getDeviceId(context)
                    UserRepository.getProfile(
                        uid = currentUser.uid,
                        onSuccess = { profile ->
                            if (profile.deviceId.isNotEmpty() && profile.deviceId != deviceId) {
                                FirebaseAuth.getInstance().signOut()
                                currentScreen = "login"
                            } else {
                                currentScreen = if (profile.role == "lecturer") "lecturerDashboard" else "studentDashboard"
                            }
                        },
                        onFailure = {
                            FirebaseAuth.getInstance().signOut()
                            currentScreen = "login"
                        }
                    )
                }
            }

            when (currentScreen) {
                "checking" -> {
                    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF080D17)), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFE7B96B))
                    }
                }

                "login" -> {
                    LoginScreen(
                        onLoginSuccess = { role -> currentScreen = if (role == "lecturer") "lecturerDashboard" else "studentDashboard" },
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
                        onLogout = { FirebaseAuth.getInstance().signOut(); currentScreen = "login" },
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
                        onLogout = { FirebaseAuth.getInstance().signOut(); currentScreen = "login" },
                        onCreateAttendance = { currentScreen = "createAttendance" },
                        onOpenAdmin = { currentScreen = "admin" }
                    )
                }

                "createAttendance" -> {
                    CreateAttendanceScreen(onBack = { currentScreen = "lecturerDashboard" })
                }

                "admin" -> {
                    AdminScreen(onBack = { currentScreen = "lecturerDashboard" })
                }
            }
        }
    }
}
