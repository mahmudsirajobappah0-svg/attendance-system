package com.example.attendancesystem2

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val sessionsRef = db.collection("sessions")
    private val attendanceRef = db.collection("attendance")

    fun createSession(
        course: String,
        lecturerId: String,
        latitude: Double,
        longitude: Double,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val docRef = sessionsRef.document()
        val session = AttendanceSession(
            sessionId = docRef.id,
            course = course,
            lecturerId = lecturerId,
            latitude = latitude,
            longitude = longitude,
            createdAt = System.currentTimeMillis(),
            active = true
        )

        docRef.set(session)
            .addOnSuccessListener { onSuccess(docRef.id) }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to create session") }
    }

    fun getSession(
        sessionId: String,
        onSuccess: (AttendanceSession) -> Unit,
        onFailure: (String) -> Unit
    ) {
        sessionsRef.document(sessionId)
            .get()
            .addOnSuccessListener { doc ->
                val session = doc.toObject(AttendanceSession::class.java)
                if (session != null) onSuccess(session) else onFailure("Session not found")
            }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to load session") }
    }

    fun markAttendance(
        record: AttendanceRecord,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        attendanceRef
            .whereEqualTo("sessionId", record.sessionId)
            .whereEqualTo("studentId", record.studentId)
            .get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    onFailure("Attendance already marked for this session")
                } else {
                    attendanceRef.add(record)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e.message ?: "Failed to save attendance") }
                }
            }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to check attendance") }
    }

    fun getStudentHistory(
        studentId: String,
        onSuccess: (List<AttendanceRecord>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        attendanceRef
            .whereEqualTo("studentId", studentId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                onSuccess(snapshot.toObjects(AttendanceRecord::class.java))
            }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to load history") }
    }
}
