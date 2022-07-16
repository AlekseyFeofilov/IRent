package com.example.shift.authorization

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shift.*
import com.example.shift.companion.SharedPreferencesObject.Companion.user
import com.example.shift.api.AuthorizationApi
import com.example.shift.authorization.data.SecurityData
import com.example.shift.companion.IrentApp
import com.example.shift.databinding.FragmentAuthorizationBinding
import com.example.shift.main.OnAuthorized
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        binding.submitButton.setOnClickListener(submit)
        return binding.root
    }

    private val submit = View.OnClickListener {
        logIn(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
    }

    private fun showWarning(warnings: String) {
        binding.warningTextView.text = warnings
    }

    private fun checkCompletion(login: String, password: String): Boolean {

        when {
            login.isEmpty() -> showWarning(getString(R.string.entry_login))
            password.isEmpty() -> showWarning(getString(R.string.entry_password))
            else -> return true
        }

        return false
    }

    private fun logIn(login: String, password: String) {
        if (!checkCompletion(login, password)) return

        IrentApp.retrofit
            .create(AuthorizationApi::class.java)
            .logIn(SecurityData(login, password))
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.body() == null) {
                        showWarning(getString(R.string.wrong_email_or_password))
                        return
                    }

                    user = response.body()
                    (requireActivity() as OnAuthorized).onAuthorized()
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("Backend", t.toString())
                }
            })
    }
}