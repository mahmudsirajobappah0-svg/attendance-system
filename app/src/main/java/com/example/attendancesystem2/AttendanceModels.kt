package com.example.attendancesystem2

data class AttendanceSession(
    val sessionId: String = "",
    val course: String = "",
    val lecturerId: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val createdAt: Long = 0L,
    val expiresAt: Long = 0L,
    val active: Boolean = true
)

data class AttendanceRecord(
    val studentId: String = "",
    val studentName: String = "",
    val course: String = "",
    val sessionId: String = "",
    val date: String = "",
    val status: String = "Present",
    val timestamp: Long = 0L
)

data class Course(
    val courseId: String = "",
    val name: String = "",
    val lecturerId: String = ""
)
