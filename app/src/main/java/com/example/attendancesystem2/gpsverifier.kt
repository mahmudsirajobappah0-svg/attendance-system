package com.example.attendancesystem2

import android.location.Location

object GPSVerifier {

    fun isWithinAllowedDistance(

        studentLatitude: Double,
        studentLongitude: Double,

        lecturerLatitude: Double,
        lecturerLongitude: Double,

        allowedDistanceMeters: Float = 100f

    ): Boolean {

        val result = FloatArray(1)

        Location.distanceBetween(

            studentLatitude,
            studentLongitude,

            lecturerLatitude,
            lecturerLongitude,

            result
        )

        return result[0] <= allowedDistanceMeters
    }
}
