package com.example.attendancesystem2

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val ScannerBackground = Color(0xFF080D17)
private val ScannerGold = Color(0xFFE7B96B)
private val ScannerWhite = Color(0xFFF5F5F5)
private val ScannerGray = Color(0xFF9BA5B5)

@Composable
fun QrScannerScreen(
    onBack: () -> Unit,
    onAttendanceMarked: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val auth = FirebaseAuth.getInstance()

    var status by remember { mutableStateOf("Tap below to scan the lecturer's QR code") }
    var loading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    fun proceedWithScan(sessionId: String) {
        loading = true
        status = "Verifying session..."
        isError = false

        FirestoreRepository.getSession(
            sessionId = sessionId,
            onSuccess = { session ->
                if (!session.active) {
                    loading = false; isError = true
                    status = "This session is no longer active"
                    return@getSession
                }

                status = "Checking your location..."

                LocationHelper.getCurrentLocation(
                    context = context,
                    onSuccess = { location ->
                        val withinRange = GPSVerifier.isWithinAllowedDistance(
                            studentLatitude = location.latitude,
                            studentLongitude = location.longitude,
                            lecturerLatitude = session.latitude,
                            lecturerLongitude = session.longitude
                        )

                        if (!withinRange) {
                            loading = false; isError = true
                            status = "You are too far from the class location"
                            return@getCurrentLocation
                        }

                        val user = auth.currentUser
                        val record = AttendanceRecord(
                            studentId = user?.uid ?: "",
                            studentName = user?.email ?: "Student",
                            course = session.course,
                            sessionId = sessionId,
                            date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()),
                            status = "Present",
                            timestamp = System.currentTimeMillis()
                        )

                        FirestoreRepository.markAttendance(
                            record = record,
                            onSuccess = {
                                loading = false; isError = false
                                status = "Attendance marked successfully!"
                                onAttendanceMarked()
                            },
                            onFailure = { error ->
                                loading = false; isError = true
                                status = error
                            }
                        )
                    },
                    onFailure = {
                        loading = false; isError = true
                        status = "Could not get your location. Enable GPS and try again."
                    }
                )
            },
            onFailure = { error ->
                loading = false; isError = true
                status = error
            }
        )
    }

    val scanLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        val sessionId = result.contents
        if (sessionId == null) {
            status = "Scan cancelled"; isError = true
        } else {
            proceedWithScan(sessionId)
        }
    }

    fun launchScanner() {
        scanLauncher.launch(
            ScanOptions()
                .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                .setPrompt("Scan the lecturer's QR code")
                .setBeepEnabled(true)
                .setOrientationLocked(true)
        )
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) launchScanner()
        else { status = "Camera permission is required to scan"; isError = true }
    }

    fun proceedToBiometric() {
        status = "Confirm it's you..."

        if (!BiometricHelper.isBiometricAvailable(activity)) {
            loading = false; isError = true
            status = "No fingerprint/face unlock set up on this device. Set one up in device settings to mark attendance."
            return
        }

        BiometricHelper.authenticate(
            activity = activity,
            onSuccess = {
                status = "Opening camera..."
                val hasCameraPermission = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

                if (hasCameraPermission) launchScanner()
                else cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            },
            onError = { error ->
                loading = false; isError = true
                status = "Verification failed: $error"
            }
        )
    }

    fun startVerifiedScan() {
        val uid = auth.currentUser?.uid ?: return
        val currentDeviceId = DeviceUtils.getDeviceId(context)

        loading = true
        status = "Verifying your device..."
        isError = false

        UserRepository.getProfile(
            uid = uid,
            onSuccess = { profile ->
                when {
                    profile.deviceId.isEmpty() -> {
                        UserRepository.bindDeviceIfEmpty(uid, currentDeviceId) {
                            proceedToBiometric()
                        }
                    }
                    profile.deviceId == currentDeviceId -> proceedToBiometric()
                    else -> {
                        loading = false; isError = true
                        status = "This account is linked to another device. Attendance can only be marked from your registered device."
                    }
                }
            },
            onFailure = { error ->
                loading = false; isError = true
                status = error
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(ScannerBackground).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "Scan Attendance", color = ScannerWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Point your camera at the lecturer's QR code", color = ScannerGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(60.dp))

        Box(
            modifier = Modifier.size(280.dp).background(Color(0xFF151E2D), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (loading) CircularProgressIndicator(color = ScannerGold)
            else Text(text = "📷", fontSize = 70.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = status,
            color = if (isError) Color(0xFFFF6B6B) else ScannerGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { startVerifiedScan() },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth().height(58.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ScannerGold, contentColor = Color.Black)
        ) {
            Text(text = "Start Scanning", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) {
            Text(text = "Back", color = ScannerGold)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
