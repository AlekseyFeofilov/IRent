package com.example.shift.authorization

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shift.api.AuthorizationApi
import com.example.shift.authorization.data.SecurityExtendData
import com.example.shift.companion.IrentApp
import com.example.shift.companion.SharedPreferencesObject.Companion.user
import com.example.shift.databinding.FragmentAuthorizationBinding
import com.example.shift.main.OnAuthorized
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogOutFragment: Fragment() {
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
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        IrentApp.retrofit
            .create(AuthorizationApi::class.java)
            .deleteAccount(SecurityExtendData(user!!.id, email, password))
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if (response.body() == null || response.body() == false) return

                    (requireActivity() as OnAuthorized).onLogOut()
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.e("Backend", t.toString())
                }
            })
    }
}