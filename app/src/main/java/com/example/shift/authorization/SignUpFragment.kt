package com.example.shift.authorization

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.shift.OnAuthorized
import com.example.shift.R
import com.example.shift.SampleApp
import com.example.shift.authorization.AuthorizationFragment.Companion.user
import com.example.shift.authorization.data.Security
import com.example.shift.authorization.data.User
import com.example.shift.databinding.FragmentSignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment(), ConfirmSignUpFragment.OnConfirmEmailListener {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var fm: FragmentManager
    private var id: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fm = requireActivity().supportFragmentManager
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.submitButton.setOnClickListener(submit)

        binding.addPhotoButton.setOnClickListener{
            getContent.launch("image/*")
        }

        return binding.root
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        binding.iconImageView.setImageURI(uri)
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
            name.isEmpty() -> showWarning("Enter name")
            surname.isEmpty() -> showWarning("Enter surname")
            email.isEmpty() -> showWarning("Enter email")
            phoneNumber.isEmpty() -> showWarning("Enter phone number")
            password.isEmpty() -> showWarning("Enter password")
            confirmPassword.isEmpty() -> showWarning("Confirm password")
            else -> return true
        }

        return false
    }

    private fun checkPasswords(password: String, confirmPassword: String): Boolean {
        when {
            password != confirmPassword -> showWarning("Passwords are different")
            password.length !in 8..32  -> showWarning("Password length must be in 8 to 32 symbols") //тестеры попросили
            Regex("[^A-Za-z_0-9]").containsMatchIn(password) -> showWarning("Password can contains only latin symbols, digital and symbol _")
            else -> return true
        }

        return false
    }

    private fun checkEmail(email: String): Boolean {
        var isContains = false

        if (SampleApp.backendToggle) {
            SampleApp.retrofit
                .create(AuthorizationApi::class.java)
                .contains(email)
                .enqueue(object : Callback<Boolean> {
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if (response.body() == null) {
                            showWarning("Something went wrong. Try again")
                            return
                        }

                        isContains = response.body()!!
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        // do something
                    }

                })
        } else {
            isContains = true
        }

        if (!isContains) {
            showWarning("This email has already signed up")
        }

        return isContains
    }

    private fun createUser(
        name: String,
        uri: String,
        surname: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): Long? {
        var id: Long? = null

        if (SampleApp.backendToggle) {
            SampleApp.retrofit
                .create(AuthorizationApi::class.java)
                .createUser(
                    Security(0L, email, password),
                    User(0L, /*uri,*/ name, surname, email, phoneNumber)
                )
                .enqueue(object : Callback<Long> {
                    override fun onResponse(call: Call<Long>, response: Response<Long>) {
                        if (response.body() == null) {
                            showWarning("Something went wrong. Try again")
                            return
                        }

                        id = response.body()
                    }

                    override fun onFailure(call: Call<Long>, t: Throwable) {
                        // do something
                    }
                })
        } else {
            id = 42
        }

        return id
    }

    private val submit = View.OnClickListener {
        showWarning("")

        val name = binding.nameEditText.text.toString()
        val surname = binding.surnameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val phoneNumber = binding.phoneNumberEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if(SampleApp.authorizationToggle) {
            if (
                !checkCompletion(name, surname, email, phoneNumber, password, confirmPassword) ||
                !checkPasswords(password, confirmPassword) ||
                !checkEmail(email)
            ) {
                return@OnClickListener
            }
        }

        val uri = Uri.parse("android.resource://com.example.shift/" + binding.iconImageView.drawable);

        if(!SampleApp.backendToggle){
            user = User(0, /*uri.toString(),*/ name, surname, email, phoneNumber)
        }

        id = createUser(name, uri.toString(), surname, email, phoneNumber, password) ?: return@OnClickListener

        binding.confirmFrameLayout.visibility = View.VISIBLE
        binding.submitButton.isEnabled = false

        fm
            .beginTransaction()
            .add(R.id.confirmFrameLayout, ConfirmSignUpFragment(this))
            .commit()
    }

    override fun onConfirmEmail(code: String) {
        if (code.isEmpty()) {
            showWarning("Enter code")
            return
        }

        if (SampleApp.backendToggle) {
            SampleApp.retrofit
                .create(AuthorizationApi::class.java)
                .confirmUser(id, binding.nameEditText.text.toString())
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.body() == null) {
                            showWarning("Something went wrong. Try again")
                            return
                        }

                        user = response.body()
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // do something
                    }

                })
        } else {
            AuthorizationFragment.saveUser(requireActivity().getSharedPreferences(AuthorizationFragment.APP_PREFERENCES,
                AppCompatActivity.MODE_PRIVATE
            ))

            (requireActivity() as OnAuthorized).onAuthorized()
        }
    }
}