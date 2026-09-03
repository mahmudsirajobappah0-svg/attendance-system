package com.example.attendancesystem2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AttendanceApp()
        }
    }
}

@Composable
fun AttendanceApp() {

    var currentScreen by mutableStateOf("login")

    MaterialTheme {

        when (currentScreen) {

            // LOGIN SCREEN
            "login" -> {

                LoginScreen(

                    onLoginSuccess = { role ->

                        currentScreen =
                            if (role == "lecturer") {
                                "lecturerDashboard"
                            } else {
                                "studentDashboard"
                            }
                    },

                    onRegisterClick = {
                        currentScreen = "register"
                    }
                )
            }


            // REGISTER SCREEN
            "register" -> {

                RegisterScreen(

                    onRegisterSuccess = {
                        currentScreen = "login"
                    },

                    onBackToLogin = {
                        currentScreen = "login"
                    }
                )
            }


            // STUDENT DASHBOARD
            "studentDashboard" -> {

                StudentDashboard(

                    onLogout = {
                        FirebaseAuth.getInstance().signOut()
                        currentScreen = "login"
                    },

                    onScanAttendance = {
                        currentScreen = "scanner"
                    }
                )
            }


            // QR SCANNER
            "scanner" -> {

                QrScannerScreen(

                    onBack = {
                        currentScreen = "studentDashboard"
                    },

                    onAttendanceMarked = {
                        currentScreen = "studentDashboard"
                    }
                )
            }


            // LECTURER DASHBOARD
            "lecturerDashboard" -> {

    LecturerDashboard(

        onLogout = {
            FirebaseAuth.getInstance().signOut()
            currentScreen = "login"
        },

        onCreateAttendance = {
            currentScreen = "createAttendance"
        }
    )
}
            }
        }
    }
}
