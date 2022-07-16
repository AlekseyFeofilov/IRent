package com.example.shift.delayed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shift.databinding.FragmentOwnBinding

class OwnFragment : Fragment() {
    private lateinit var binding: FragmentOwnBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnBinding.inflate(inflater, container, false)

        return binding.root
    }
}