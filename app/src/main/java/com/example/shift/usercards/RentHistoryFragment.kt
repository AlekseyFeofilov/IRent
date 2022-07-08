package com.example.shift.usercards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shift.databinding.FragmentRentHistoryBinding

class RentHistoryFragment : Fragment() {
    private lateinit var binding: FragmentRentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRentHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }
}