package com.example.shift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.shift.authorization.AuthorizationActivity
import com.example.shift.databinding.ActivityMainBinding
import com.example.shift.utils.setupWithNavControllerAndOnSelectedListener

class MainActivity : AppCompatActivity(), OnMenuItemSelectedListener {
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

        binding.navView.setupWithNavControllerAndOnSelectedListener(navController, this)
        binding.buttonNavView.setupWithNavControllerAndOnSelectedListener(navController, this)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setGroupCheckable(isBacklight: Boolean) {
        binding.buttonNavView.menu.setGroupCheckable(0, isBacklight, true)
    }

    override fun onMenuItemSelectedListener(item: MenuItem) {
        binding.drawerLayout.close()

        setGroupCheckable(
            when (item.itemId) {
                R.id.feedFragment -> true
                R.id.createCardFragment -> true
                R.id.chatFragment -> true
                R.id.sidebar -> {
                    binding.drawerLayout.open()
                    true
                }
                R.id.logOutButton -> {
                    val sharedPreferences = getSharedPreferences(AuthorizationActivity.APP_PREFERENCES, MODE_PRIVATE)
                    sharedPreferences.edit().remove(AuthorizationActivity.APP_PREFERENCES_USER).apply()
                    AuthorizationActivity.user = null
                    false
                }
                else -> false
            }
        )
    }
}