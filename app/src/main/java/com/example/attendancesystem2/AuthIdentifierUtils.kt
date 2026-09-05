package com.example.attendancesystem2

object AuthIdentifierUtils {
    fun buildAuthEmail(matricNo: String): String {
        val cleaned = matricNo.trim().lowercase().replace(Regex("[^a-z0-9]"), "")
        return "$cleaned@attendance.local"
    }
}
