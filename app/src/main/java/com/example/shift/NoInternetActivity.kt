package com.example.shift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shift.databinding.ActivityNoInternerBinding
import com.example.shift.main.MainActivity

class NoInternetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoInternerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInternerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tryAgainButton.setOnClickListener {
            if(isOnline(this)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}