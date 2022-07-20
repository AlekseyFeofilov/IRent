package com.example.shift

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shift.main.MainActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(
            this,
            if (isOnline(this)) MainActivity::class.java else NoInternetActivity::class.java
        )

        startActivity(intent)
        finish()
    }
}