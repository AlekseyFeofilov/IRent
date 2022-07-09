package com.example.shift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.shift.authorization.AuthorizationActivity
import com.example.shift.authorization.data.User
import com.example.shift.databinding.ActivityMainBinding
import com.example.shift.utils.setupWithNavControllerAndOnSelectedListener
import com.google.gson.Gson

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

        AuthorizationActivity.user = Gson().fromJson(
            getSharedPreferences(AuthorizationActivity.APP_PREFERENCES, MODE_PRIVATE).
            getString(AuthorizationActivity.
            APP_PREFERENCES_USER, ""),
            User::class.java)

        tuneHeader()
    }

    private fun tuneHeader() {
        val header = binding.navView.getHeaderView(0)

        if (AuthorizationActivity.user == null) {
            header.findViewById<TextView>(R.id.titleTextView).text = "You're not authorized"
            return
        }

        header.findViewById<TextView>(R.id.titleTextView).text =
            "${AuthorizationActivity.user!!.name} ${AuthorizationActivity.user!!.surname}"
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
                    val sharedPreferences =
                        getSharedPreferences(AuthorizationActivity.APP_PREFERENCES, MODE_PRIVATE)
                    sharedPreferences.edit().remove(AuthorizationActivity.APP_PREFERENCES_USER)
                        .apply()
                    AuthorizationActivity.user = null
                    tuneHeader()
                    false
                }
                else -> false
            }
        )
    }
}