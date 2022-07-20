package com.example.shift.delayed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shift.databinding.FragmentChatBinding
import com.example.shift.main.NavControllerActivity

class ChatFragment  : Fragment() {
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        (requireActivity() as NavControllerActivity).showMessage("This fragment is developing")
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as NavControllerActivity).showMessage("")
    }
}