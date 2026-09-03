package com.example.attendancesystem2

import com.google.firebase.firestore.FirebaseFirestore

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "student",
    val deviceId: String = ""
)

object UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("users")

    fun createProfile(
        profile: UserProfile,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        usersRef.document(profile.uid)
            .set(profile)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to save profile") }
    }

    fun getProfile(
        uid: String,
        onSuccess: (UserProfile) -> Unit,
        onFailure: (String) -> Unit
    ) {
        usersRef.document(uid)
            .get()
            .addOnSuccessListener { doc ->
                val profile = doc.toObject(UserProfile::class.java)
                if (profile != null) onSuccess(profile) else onFailure("Profile not found")
            }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to load profile") }
    }

    fun bindDeviceIfEmpty(
        uid: String,
        deviceId: String,
        onDone: () -> Unit
    ) {
        usersRef.document(uid).update("deviceId", deviceId)
            .addOnSuccessListener { onDone() }
            .addOnFailureListener { onDone() }
    }
}
