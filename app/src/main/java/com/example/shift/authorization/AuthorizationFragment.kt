package com.example.shift.authorization

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.shift.R
import com.example.shift.SampleApp
import com.example.shift.authorization.data.User
import com.example.shift.databinding.FragmentAuthorizationBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        var user: User? = null

        const val APP_PREFERENCES_USER = "User"
        const val APP_PREFERENCES = "Settings"

        fun saveUser(sharedPreferences: SharedPreferences){
            sharedPreferences
                .edit()
                .putString(APP_PREFERENCES_USER, Gson().toJson(user))
                .apply()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            AuthorizationFragment.APP_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )

        binding.submitButton.setOnClickListener(submit)
        return binding.root
    }

    private val submit = View.OnClickListener {
        logIn(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
        saveUser(sharedPreferences)
    }

    private fun showWarning(warning: String){
        binding.warningTextView.text = warning
    }

    private fun checkCompletion(login: String, password: String): Boolean{
        when{
            login.isEmpty() -> showWarning("Entry login")
            password.isEmpty() -> showWarning("Entry password")
            else -> return true
        }

        return false
    }

    private fun logIn(login: String, password: String) {
        if(!checkCompletion(login, password)) return

        if (SampleApp.backendToggle) {
            SampleApp.retrofit
                .create(AuthorizationApi::class.java)
                .getUser(login, password)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        user = response.body()
                        //todo: Go to another activity
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // do something
                    }

                })
        } else {
            showWarning("Wrong login or password")
        }
    }
}