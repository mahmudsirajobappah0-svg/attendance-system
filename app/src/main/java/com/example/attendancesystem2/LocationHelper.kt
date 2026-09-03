package com.example.attendancesystem2

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Handler
import android.os.Looper
import com.google.android.gms.location.CancellationTokenSource
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
object LocationHelper {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        onSuccess: (Location) -> Unit,
        onFailure: () -> Unit
    ) {
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)
        val cancellationSource = CancellationTokenSource()
        val handler = Handler(Looper.getMainLooper())
        var completed = false

        // Safety net: if nothing resolves within 15s, cancel and fall back
        val timeoutRunnable = Runnable {
            if (!completed) {
                completed = true
                cancellationSource.cancel()
                fusedClient.lastLocation
                    .addOnSuccessListener { last ->
                        if (last != null) onSuccess(last) else onFailure()
                    }
                    .addOnFailureListener { onFailure() }
            }
        }
        handler.postDelayed(timeoutRunnable, 15000L)

        fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationSource.token)
            .addOnSuccessListener { location ->
                if (!completed) {
                    completed = true
                    handler.removeCallbacks(timeoutRunnable)
                    if (location != null) onSuccess(location) else onFailure()
                }
            }
            .addOnFailureListener {
                if (!completed) {
                    completed = true
                    handler.removeCallbacks(timeoutRunnable)
                    onFailure()
                }
            }
    }
}
