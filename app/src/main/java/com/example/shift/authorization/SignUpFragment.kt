package com.example.shift.authorization

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.example.shift.R
import com.example.shift.companion.IrentApp
import com.example.shift.main.MainActivity
import com.example.shift.companion.SharedPreferencesObject.Companion.APP_PREFERENCES_ID
import com.example.shift.api.AuthorizationApi
import com.example.shift.authorization.data.SignUpData
import com.example.shift.databinding.FragmentSignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var fm: FragmentManager
    private lateinit var activity: MainActivity
    private var currentUri = Uri.parse("android.resource://com.example.shift/" + R.drawable.ic_user)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity = requireActivity() as MainActivity
        fm = requireActivity().supportFragmentManager
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.submitButton.setOnClickListener(submit)

        binding.addPhotoButton.setOnClickListener {
            getContent.launch("image/*")
        }

        return binding.root
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            binding.iconImageView.setImageURI(uri)
            currentUri = uri
        }

    private fun showWarning(warning: String) {
        binding.warningTextView.text = warning
    }

    private fun checkCompletion(
        name: String,
        surname: String,
        email: String,
        phoneNumber: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        when {
            name.isEmpty() -> showWarning(getString(R.string.enter_name))
            surname.isEmpty() -> showWarning(getString(R.string.enter_surname))
            email.isEmpty() -> showWarning(getString(R.string.enter_email))
            phoneNumber.isEmpty() -> showWarning(getString(R.string.enter_phone_number))
            password.isEmpty() -> showWarning(getString(R.string.enter_password))
            confirmPassword.isEmpty() -> showWarning(getString(R.string.confirm_password))
            else -> return true
        }

        return false
    }

    private fun checkPasswords(password: String, confirmPassword: String): Boolean {
        when {
            password != confirmPassword -> showWarning(getString(R.string.passwords_are_different))
            password.length < 6 -> showWarning(getString(R.string.password_length_must_be_more_then_5_symbols))
            Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$)[A-Za-z0-9!@%&]{8,}$").containsMatchIn(
                password
            ) -> showWarning(getString(R.string.password_can_contains_only_latin_symbols_digital_and_symbols))
            else -> return true
        }

        return false
    }

    private fun createUser(
        name: String,
        //uri: String,
        surname: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) {
        activity.onProgressStart()

        IrentApp.retrofit
            .create(AuthorizationApi::class.java)
            .signUp(SignUpData(name, surname, phoneNumber, email, password))
            .enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.body() == null) {
                        showWarning("Something went wrong. Try again")
                        return
                    }

                    val id = response.body().toString().toLong()
                    activity.getSharedPreferences().edit().putLong(APP_PREFERENCES_ID, id).apply()

                    val navController = NavHostFragment.findNavController(this@SignUpFragment)
                    navController.navigate(R.id.confirmSignUpFragment)

                    activity.onProgressEnd()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Backend", t.toString())
                    activity.onProgressEnd()
                }
            })
    }

    private val submit = View.OnClickListener {
        showWarning("")

        val name = binding.nameEditText.text.toString()
        val surname = binding.surnameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val phoneNumber = binding.phoneNumberEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if (IrentApp.validationToggle) {
            if (
                !checkCompletion(name, surname, email, phoneNumber, password, confirmPassword) ||
                !checkPasswords(password, confirmPassword)
            ) {
                return@OnClickListener
            }
        }

        //val uri = (requireActivity() as OnImageUpload).onImageUpload(currentUri)
        createUser(name, /*uri.toString(),*/ surname, email, phoneNumber, password)
    }
}