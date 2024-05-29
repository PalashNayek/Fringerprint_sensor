package com.palash.fringerprint_sensor.repositories

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    fun authenticate(
        activity: FragmentActivity,
        callback: BiometricPrompt.AuthenticationCallback
    ) {
        val executor = activity.mainExecutor
        val biometricPrompt = BiometricPrompt(activity, executor, callback)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Authenticate using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}