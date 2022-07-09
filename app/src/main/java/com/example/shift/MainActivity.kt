package com.example.shift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.shift.databinding.ActivityMainBinding
import com.example.shift.utils.setupWithNavControllerAndDrawerLayout
import com.example.shift.utils.setupWithNavControllerLogoutAndButtonNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host = fragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment? ?: return
        val navController = host.navController
        val appBarConfiguration =
            AppBarConfiguration(navController.graph)

        binding.navView.setupWithNavControllerLogoutAndButtonNavigationView(binding.buttonNavView, navController, R.id.logOutButton)
        binding.buttonNavView.setupWithNavControllerAndDrawerLayout(navController, binding.drawerLayout, R.id.sidebar)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}