package com.example.attendancesystem2

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

object LocationHelper {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        onSuccess: (Location) -> Unit,
        onFailure: () -> Unit
    ) {
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)

        fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) onSuccess(location) else onFailure()
            }
            .addOnFailureListener { onFailure() }
    }
}
