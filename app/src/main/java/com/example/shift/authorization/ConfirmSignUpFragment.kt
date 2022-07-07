package com.example.shift.authorization

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.shift.R
import com.example.shift.databinding.FragmentConfirmSignUpBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

class ConfirmSignUpFragment : Fragment() {
    private lateinit var binding: FragmentConfirmSignUpBinding

    interface OnConfirmEmailListener{
        fun onConfirmEmail(code: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmSignUpBinding.inflate(layoutInflater)
        val rootView = inflater.inflate(R.layout.fragment_confirm_sign_up, container, false)

        //todo: find the problem related with binding not working
        val captchaButton = rootView.findViewById<Button>(R.id.captchaButton)
        val confirmButton = rootView.findViewById<Button>(R.id.confirmButton)
        val codeEditText = rootView.findViewById<EditText>(R.id.codeEditText)

        rootView.findViewById<Button>(R.id.confirmButton).setOnClickListener{
            (context as OnConfirmEmailListener).onConfirmEmail(codeEditText.text.toString())
        }

        captchaButton.setOnClickListener{
            val context = requireContext()

            SafetyNet.getClient(context).verifyWithRecaptcha("6LcG3tAgAAAAAPE3Lvvtfd--yv1GY-T_rqCJLAhw")
                .addOnSuccessListener(requireActivity()/*context as Executor*/, OnSuccessListener { response ->
                    // Indicates communication with reCAPTCHA service was
                    // successful.
                    val userResponseToken = response.tokenResult
                    if (response.tokenResult?.isNotEmpty() == true) {
                        confirmButton.isEnabled = true
                        confirmButton.backgroundTintMode = null
                        confirmButton.setTextColor(Color.BLACK)
                        codeEditText.isEnabled = true
                    }
                })
                .addOnFailureListener(requireActivity()/*context as Executor*/, OnFailureListener { e ->
                    if (e is ApiException) {
                        // An error occurred when communicating with the
                        // reCAPTCHA service. Refer to the status code to
                        // handle the error appropriately.
                        Log.d(TAG, "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}")
                    } else {
                        // A different, unknown type of error occurred.
                        Log.d(TAG, "Error: ${e.message}")
                    }
                })
        }

        return rootView
    }
}