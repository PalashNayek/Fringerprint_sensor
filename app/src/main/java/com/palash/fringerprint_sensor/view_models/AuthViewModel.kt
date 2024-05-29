package com.palash.fringerprint_sensor.view_models

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.palash.fringerprint_sensor.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun authenticate(activity: FragmentActivity, callback: BiometricPrompt.AuthenticationCallback) {
        authRepository.authenticate(activity, callback)
    }
}