package com.example.attendancesystem2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var currentScreen by mutableStateOf("login")

            when (currentScreen) {

                "login" -> {
                    LoginScreen(
                        onLogin = {
                            currentScreen = "dashboard"
                        }
                    )
                }

                "dashboard" -> {
                    StudentDashboard(
                        onScanAttendance = {
                            currentScreen = "scanner"
                        },

                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            currentScreen = "login"
                        }
                    )
                }

                "scanner" -> {
                    QrScannerScreen(
                        onBack = {
                            currentScreen = "dashboard"
                        }
                    )
                }

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
            }
        }
    }
}
