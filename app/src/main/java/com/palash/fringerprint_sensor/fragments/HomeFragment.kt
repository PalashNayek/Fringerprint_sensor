package com.palash.fringerprint_sensor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.palash.fringerprint_sensor.R
import com.palash.fringerprint_sensor.databinding.FragmentHomeBinding
import com.palash.fringerprint_sensor.view_models.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val biometricManager = BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Biometric authentication is available
                binding.buttonAuthenticate.isVisible = true
                binding.buttonAuthenticate.setOnClickListener {
                    authenticateUser()
                }
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                binding.textViewStatus.text = "No biometric hardware available"
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                binding.textViewStatus.text = "Biometric hardware is currently unavailable"
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                binding.textViewStatus.text = "No biometric data enrolled"
            }
        }
    }


    private fun authenticateUser() {
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                binding.textViewStatus.text = "Authentication error: $errString"
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                binding.textViewStatus.text = "Authentication succeeded"
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                binding.textViewStatus.text = "Authentication failed"
            }
        }

        authViewModel.authenticate(requireActivity(), callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}