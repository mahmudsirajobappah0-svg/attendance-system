package com.example.attendancesystem2

import com.google.firebase.auth.FirebaseAuth

object FirebaseManager {

    val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    fun logout() {
        auth.signOut()
    }

    fun currentUserEmail(): String? {
        return auth.currentUser?.email
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
