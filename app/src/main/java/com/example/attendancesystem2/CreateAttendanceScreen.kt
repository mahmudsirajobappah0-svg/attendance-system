package com.example.attendancesystem2

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

private val Background = Color(0xFF080D17)
private val Gold = Color(0xFFE7B96B)
private val White = Color(0xFFF5F5F5)
private val Gray = Color(0xFF9BA5B5)

@Composable
fun CreateAttendanceScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var course by remember { mutableStateOf("") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasLocationPermission = granted
        if (!granted) message = "Location permission is required to create a session"
    }

    fun generateQr(sessionId: String) {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(sessionId, BarcodeFormat.QR_CODE, 512, 512)
        val bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bitmap.setPixel(
                    x, y,
                    if (bitMatrix[x, y]) android.graphics.Color.BLACK
                    else android.graphics.Color.WHITE
                )
            }
        }
        qrBitmap = bitmap
    }

    fun createSession() {
        if (course.isBlank()) {
            message = "Please enter a course name"
            return
        }
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        loading = true
        message = ""

        LocationHelper.getCurrentLocation(
            context = context,
            onSuccess = { location ->
                val lecturerId = auth.currentUser?.uid ?: ""
                FirestoreRepository.createSession(
                    course = course.trim(),
                    lecturerId = lecturerId,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    onSuccess = { newSessionId ->
                        loading = false
                        generateQr(newSessionId)
                        message = "Session created! Show this QR to students."
                    },
                    onFailure = { error ->
                        loading = false
                        message = error
                    }
                )
            },
            onFailure = {
                loading = false
                message = "Could not get your location. Enable GPS and try again."
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Create Attendance Session",
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(25.dp))

        if (qrBitmap == null) {
            OutlinedTextField(
                value = course,
                onValueChange = { course = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Course name") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Gold,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = Gold,
                    focusedLabelColor = Gold,
                    unfocusedLabelColor = Gray
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (message.isNotEmpty()) {
                Text(text = message, color = Color(0xFFFF6B6B), fontSize = 13.sp)
                Spacer(modifier = Modifier.height(15.dp))
            }

            Button(
                onClick = { createSession() },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color.Black)
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black, strokeWidth = 3.dp)
                } else {
                    Text("Generate QR Code", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Image(
                    bitmap = qrBitmap!!.asImageBitmap(),
                    contentDescription = "Attendance QR Code",
                    modifier = Modifier.size(260.dp).padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = course, color = Gold, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = message, color = Color(0xFF66E08A), fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) {
            Text(text = "Back to Dashboard", color = Gold)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
