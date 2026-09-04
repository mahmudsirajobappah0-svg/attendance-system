package com.example.attendancesystem2

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Handler
import android.os.Looper
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

object LocationHelper {

    // Reject fixes worse than this; keep trying until we get something usable or time out
    private const val ACCEPTABLE_ACCURACY_METERS = 30f
    private const val MAX_WAIT_MS = 20000L
    private const val POLL_INTERVAL_MS = 2000L

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        onSuccess: (Location) -> Unit,
        onFailure: () -> Unit,
        onProgress: ((attempt: Int, bestAccuracy: Float?) -> Unit)? = null
    ) {
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)
        val handler = Handler(Looper.getMainLooper())
        var completed = false
        var bestSoFar: Location? = null
        var attempt = 0
        val startTime = System.currentTimeMillis()

        fun finish(location: Location?) {
            if (completed) return
            completed = true
            if (location != null) onSuccess(location) else onFailure()
        }

        fun tryOnce() {
            if (completed) return
            attempt++

            fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    if (completed) return@addOnSuccessListener

                    if (location != null) {
                        if (bestSoFar == null || location.accuracy < bestSoFar!!.accuracy) {
                            bestSoFar = location
                        }
                    }

                    onProgress?.invoke(attempt, bestSoFar?.accuracy)

                    val goodEnough = bestSoFar != null && bestSoFar!!.accuracy <= ACCEPTABLE_ACCURACY_METERS
                    val timedOut = System.currentTimeMillis() - startTime >= MAX_WAIT_MS

                    when {
                        goodEnough -> finish(bestSoFar)
                        timedOut -> finish(bestSoFar) // use best we found, even if imperfect
                        else -> handler.postDelayed({ tryOnce() }, POLL_INTERVAL_MS)
                    }
                }
                .addOnFailureListener {
                    if (completed) return@addOnFailureListener
                    val timedOut = System.currentTimeMillis() - startTime >= MAX_WAIT_MS
                    if (timedOut) {
                        finish(bestSoFar)
                    } else {
                        handler.postDelayed({ tryOnce() }, POLL_INTERVAL_MS)
                    }
                }
        }

        tryOnce()
    }
}
