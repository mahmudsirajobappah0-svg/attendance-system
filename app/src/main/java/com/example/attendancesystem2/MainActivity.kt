package com.example.attendancesystem2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.attendancesystem2.ui.theme.AttendanceSystem2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AttendanceSystem2Theme {

                var currentScreen by remember {
                    mutableStateOf("login")
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when (currentScreen) {

                        "login" -> {
                            LoginScreen(
                                onLogin = {
                                    currentScreen = "dashboard"
                                }
                            )
                        }

                        "dashboard" -> {
                            StudentDashboard()
                        }
                    }
                }
            }
        }
    }
}
