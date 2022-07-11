package com.example.shift.authorization

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shift.SampleApp
import com.example.shift.databinding.FragmentConfirmSignUpBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

class ConfirmSignUpFragment(private val signUpFragment: SignUpFragment) : Fragment() {
    private lateinit var binding: FragmentConfirmSignUpBinding

    interface OnConfirmEmailListener {
        fun onConfirmEmail(code: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmSignUpBinding.inflate(inflater, container, false)
        binding.confirmButton.setOnClickListener {
            signUpFragment.onConfirmEmail(binding.codeEditText.text.toString())
        }

        binding.captchaButton.setOnClickListener {
            val context = requireContext()

            if (SampleApp.captchaToggle) {
                SafetyNet.getClient(context)
                    .verifyWithRecaptcha("6LcG3tAgAAAAAPE3Lvvtfd--yv1GY-T_rqCJLAhw")
                    .addOnSuccessListener(
                        requireActivity()/*context as Executor*/,
                        OnSuccessListener { response ->
                            // Indicates communication with reCAPTCHA service was
                            // successful.
                            val userResponseToken = response.tokenResult
                            if (response.tokenResult?.isNotEmpty() == true) {
                                binding.confirmButton.isEnabled = true
                                binding.confirmButton.backgroundTintMode = null
                                binding.confirmButton.setTextColor(Color.BLACK)
                                binding.codeEditText.isEnabled = true
                            }
                        })
                    .addOnFailureListener(
                        requireActivity()/*context as Executor*/,
                        OnFailureListener { e ->
                            if (e is ApiException) {
                                // An error occurred when communicating with the
                                // reCAPTCHA service. Refer to the status code to
                                // handle the error appropriately.
                                Log.d(
                                    TAG,
                                    "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}"
                                )
                            } else {
                                // A different, unknown type of error occurred.
                                Log.d(TAG, "Error: ${e.message}")
                            }
                        })
            } else {
                binding.confirmButton.isEnabled = true
                binding.confirmButton.backgroundTintMode = null
                binding.confirmButton.setTextColor(Color.BLACK)
                binding.codeEditText.isEnabled = true
            }
        }

        return binding.root
    }
}