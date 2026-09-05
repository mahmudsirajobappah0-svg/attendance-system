package com.example.attendancesystem2

import android.location.Location

object GPSVerifier {
    fun isWithinAllowedDistance(
        studentLocation: Location,
        lecturerLatitude: Double,
        lecturerLongitude: Double,
        allowedDistanceMeters: Float = 150f
    ): Boolean {
        val result = FloatArray(1)
        Location.distanceBetween(
            studentLocation.latitude,
            studentLocation.longitude,
            lecturerLatitude,
            lecturerLongitude,
            result
        )
        val distance = result[0]
        val accuracyAllowance = studentLocation.accuracy.coerceAtMost(50f)
        return distance <= (allowedDistanceMeters + accuracyAllowance)
    }
}
