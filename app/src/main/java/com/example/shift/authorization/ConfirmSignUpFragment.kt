package com.example.shift.authorization

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shift.*
import com.example.shift.companion.SharedPreferencesObject.Companion.user
import com.example.shift.api.AuthorizationApi
import com.example.shift.authorization.data.ConfirmData
import com.example.shift.companion.IrentApp
import com.example.shift.databinding.FragmentConfirmSignUpBinding
import com.example.shift.main.MainActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmSignUpFragment : Fragment() {
    private lateinit var binding: FragmentConfirmSignUpBinding
    private lateinit var activity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmSignUpBinding.inflate(inflater, container, false)
        activity = requireActivity() as MainActivity
        binding.confirmButton.setOnClickListener {
            confirmEmail(binding.codeEditText.text.toString())
        }

        binding.captchaButton.setOnClickListener(captcha)
        return binding.root
    }

    private val captcha = View.OnClickListener {
        val context = requireContext()

        if (IrentApp.captchaToggle) {
            SafetyNet.getClient(context)
                .verifyWithRecaptcha("6LcG3tAgAAAAAPE3Lvvtfd--yv1GY-T_rqCJLAhw")
                .addOnSuccessListener(
                    activity,
                    OnSuccessListener { response ->
                        if (response.tokenResult?.isNotEmpty() == true) {
                            binding.confirmButton.isEnabled = true
                            binding.confirmButton.visibility = View.VISIBLE
                        }
                    })
                .addOnFailureListener(
                    activity,
                    OnFailureListener { e ->
                        if (e is ApiException) {
                            Log.d(
                                TAG,
                                "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}"
                            )
                        } else {
                            Log.d(TAG, "Error: ${e.message}")
                        }
                    })
        } else {
            binding.confirmButton.isEnabled = true
            binding.confirmButton.visibility = View.VISIBLE
        }
    }

    private fun showWarning(warning: String) {
        binding.warningTextView.text = warning
    }

    private fun confirmEmail(code: String) {
        if (code.isEmpty()) {
            showWarning(getString(R.string.enter_code))
            return
        }

        val id = activity.getID()
        confirmEmail(id, code)
    }

    private fun confirmEmail(id: Long, code: String){
        if (IrentApp.confirmEmail) {
            IrentApp.retrofit
                .create(AuthorizationApi::class.java)
                .confirmUser(ConfirmData(id, code))
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.body() == null) {
                            showWarning("Something went wrong. Try again")
                            return
                        }

                        user = response.body()
                        activity.onAuthorized()
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {

                    }

                })
        } else {
            activity.onAuthorized()
        }
    }
}