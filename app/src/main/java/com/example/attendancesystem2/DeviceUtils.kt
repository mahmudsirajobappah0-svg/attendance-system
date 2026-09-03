package com.example.attendancesystem2

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object DeviceUtils {
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: "unknown_device"
    }
}
