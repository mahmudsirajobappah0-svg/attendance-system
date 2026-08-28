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

                // LOGIN SCREEN
                "login" -> {
                    LoginScreen(
                        onLogin = {
                            currentScreen = "dashboard"
                        }
                    )
                }


                // STUDENT DASHBOARD
                "dashboard" -> {
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


                // QR SCANNER SCREEN
                "scanner" -> {
                    QrScannerScreen(
                        onBack = {
                            currentScreen = "dashboard"
                        }
                    )
                }


                // REGISTER SCREEN
                "register" -> {
                    RegisterScreen()
                }
            }
        }
    }
}
