package com.example.shift.authorization

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shift.R
import com.example.shift.api.AuthorizationApi
import com.example.shift.card.info.CardInfo
import com.example.shift.companion.IrentApp
import com.example.shift.databinding.FragmentSettingsBinding
import com.example.shift.main.MainActivity
import com.example.shift.main.NavControllerActivity
import com.example.shift.main.OnAuthorized
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.deleteAccountButton.setOnClickListener(deleteAccount)
        return binding.root
    }

    private val deleteAccount = View.OnClickListener {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.logOutFragment, LogOutFragment())
            .commit()
    }
}