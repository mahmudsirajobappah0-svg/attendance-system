package com.example.attendancesystem2

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

object FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val sessionsRef = db.collection("sessions")
    private val attendanceRef = db.collection("attendance")
    private val coursesRef = db.collection("courses")
    private val usersRef = db.collection("users")

    fun createSession(
        course: String,
        lecturerId: String,
        latitude: Double,
        longitude: Double,
        durationMinutes: Int = 15,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val docRef = sessionsRef.document()
        val now = System.currentTimeMillis()
        val session = AttendanceSession(
            sessionId = docRef.id,
            course = course,
            lecturerId = lecturerId,
            latitude = latitude,
            longitude = longitude,
            createdAt = now,
            expiresAt = now + (durationMinutes * 60_000L),
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
                when {
                    session == null -> onFailure("Session not found")
                    !session.active -> onFailure("This session has been ended by the lecturer")
                    System.currentTimeMillis() > session.expiresAt -> onFailure("This QR code has expired")
                    else -> onSuccess(session)
                }
            }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to load session") }
    }

    fun endSession(sessionId: String, onDone: () -> Unit) {
        sessionsRef.document(sessionId).update("active", false)
            .addOnCompleteListener { onDone() }
    }

    fun listenToAttendeeCount(
        sessionId: String,
        onUpdate: (Int) -> Unit
    ): ListenerRegistration {
        return attendanceRef
            .whereEqualTo("sessionId", sessionId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) onUpdate(snapshot.size())
            }
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
            .addOnSuccessListener { snapshot -> onSuccess(snapshot.toObjects(AttendanceRecord::class.java)) }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to load history") }
    }

    fun getSessionAttendance(
        sessionId: String,
        onSuccess: (List<AttendanceRecord>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        attendanceRef
            .whereEqualTo("sessionId", sessionId)
            .get()
            .addOnSuccessListener { snapshot -> onSuccess(snapshot.toObjects(AttendanceRecord::class.java)) }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to load attendance") }
    }

    // ---- Multi-course support ----

    fun addCourse(name: String, lecturerId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val docRef = coursesRef.document()
        docRef.set(Course(courseId = docRef.id, name = name, lecturerId = lecturerId))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to add course") }
    }

    fun getLecturerCourses(
        lecturerId: String,
        onSuccess: (List<Course>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        coursesRef.whereEqualTo("lecturerId", lecturerId)
            .get()
            .addOnSuccessListener { snapshot -> onSuccess(snapshot.toObjects(Course::class.java)) }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to load courses") }
    }

    // ---- Admin device reset ----

    fun resetDeviceByEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        usersRef.whereEqualTo("email", email.trim())
            .get()
            .addOnSuccessListener { snapshot ->
                val doc = snapshot.documents.firstOrNull()
                if (doc == null) {
                    onFailure("No account found with that email")
                } else {
                    doc.reference.update("deviceId", "")
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e.message ?: "Failed to reset device") }
                }
            }
            .addOnFailureListener { e -> onFailure(e.message ?: "Lookup failed") }
    }
}
