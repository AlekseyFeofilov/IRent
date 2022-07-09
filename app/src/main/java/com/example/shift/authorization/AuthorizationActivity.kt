package com.example.shift.authorization

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.shift.SampleApp
import com.example.shift.authorization.data.User
import com.example.shift.databinding.ActivityAuthorizationBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorizationBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson

    companion object {
        var user: User? = null

        const val APP_PREFERENCES_USER = "User"
        const val APP_PREFERENCES = "Settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        gson = Gson()

        binding.submitButton.setOnClickListener(submit)
        binding.signUpButton.setOnClickListener(toSignUpActivity)
    }

    private val toSignUpActivity = View.OnClickListener {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val submit = View.OnClickListener {
        logIn(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
        saveUser()
    }

    private fun saveUser() {
        sharedPreferences
            .edit()
            .putString(APP_PREFERENCES_USER, gson.toJson(user))
            .apply()
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