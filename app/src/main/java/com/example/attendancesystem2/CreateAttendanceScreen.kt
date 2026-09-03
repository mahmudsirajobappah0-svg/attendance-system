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
import com.google.firebase.firestore.ListenerRegistration
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

private val Background = Color(0xFF080D17)
private val FieldColor = Color(0xFF151E2D)
private val Gold = Color(0xFFE7B96B)
private val White = Color(0xFFF5F5F5)
private val Gray = Color(0xFF9BA5B5)

@Composable
fun CreateAttendanceScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val lecturerId = auth.currentUser?.uid ?: ""

    var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
    var newCourseName by remember { mutableStateOf("") }
    var selectedCourse by remember { mutableStateOf("") }

    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var currentSessionId by remember { mutableStateOf("") }
    var attendeeCount by remember { mutableStateOf(0) }
    var sessionEnded by remember { mutableStateOf(false) }

    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var listener by remember { mutableStateOf<ListenerRegistration?>(null) }

    DisposableEffect(Unit) {
        onDispose { listener?.remove() }
    }

    LaunchedEffect(Unit) {
        FirestoreRepository.getLecturerCourses(
            lecturerId = lecturerId,
            onSuccess = { list -> courses = list },
            onFailure = { }
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
                    if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                )
            }
        }
        qrBitmap = bitmap
    }

    fun createSession() {
        if (selectedCourse.isBlank()) {
            message = "Please select or add a course"
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
                FirestoreRepository.createSession(
                    course = selectedCourse,
                    lecturerId = lecturerId,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    durationMinutes = 15,
                    onSuccess = { newSessionId ->
                        loading = false
                        currentSessionId = newSessionId
                        sessionEnded = false
                        generateQr(newSessionId)
                        message = "Session active for 15 minutes. Show this QR to students."

                        listener = FirestoreRepository.listenToAttendeeCount(newSessionId) { count ->
                            attendeeCount = count
                        }
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

    fun endSession() {
        FirestoreRepository.endSession(currentSessionId) {
            sessionEnded = true
            message = "Session ended. QR code is no longer valid."
        }
    }

    fun exportCsv() {
        FirestoreRepository.getSessionAttendance(
            sessionId = currentSessionId,
            onSuccess = { records -> CsvExporter.exportAttendance(context, selectedCourse, records) },
            onFailure = { message = "Could not load attendance for export" }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Background).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Create Attendance Session", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        if (qrBitmap == null) {
            if (courses.isNotEmpty()) {
                Text(text = "Select a course", color = White, fontSize = 15.sp, modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    courses.forEach { course ->
                        val isSelected = selectedCourse == course.name
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(
                                    if (isSelected) Gold.copy(alpha = 0.15f) else FieldColor,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = course.name, color = if (isSelected) Gold else White)
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedCourse = course.name },
                                colors = RadioButtonDefaults.colors(selectedColor = Gold)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))
            }

            Text(text = "Or add a new course", color = Gray, fontSize = 13.sp, modifier = Modifier.align(Alignment.Start))

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newCourseName,
                    onValueChange = { newCourseName = it },
                    modifier = Modifier.weight(1f),
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

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (newCourseName.isNotBlank()) {
                            FirestoreRepository.addCourse(
                                name = newCourseName.trim(),
                                lecturerId = lecturerId,
                                onSuccess = {
                                    courses = courses + Course(name = newCourseName.trim(), lecturerId = lecturerId)
                                    selectedCourse = newCourseName.trim()
                                    newCourseName = ""
                                },
                                onFailure = { message = it }
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color.Black)
                ) {
                    Text("Add")
                }
            }

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
                if (loading) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black, strokeWidth = 3.dp)
                else Text("Generate QR Code", fontWeight = FontWeight.Bold)
            }
        } else {
            Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Image(
                    bitmap = qrBitmap!!.asImageBitmap(),
                    contentDescription = "Attendance QR Code",
                    modifier = Modifier.size(240.dp).padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = selectedCourse, color = Gold, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (sessionEnded) "Session ended" else "$attendeeCount student(s) checked in",
                color = if (sessionEnded) Gray else Color(0xFF66E08A),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!sessionEnded) {
                Button(
                    onClick = { endSession() },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B6B), contentColor = Color.White)
                ) {
                    Text("End Session", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            OutlinedButton(
                onClick = { exportCsv() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Export Attendance (CSV)", color = Gold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) {
            Text(text = "Back to Dashboard", color = Gold)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
