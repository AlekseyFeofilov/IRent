package com.example.shift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.shift.authorization.AuthorizationFragment
import com.example.shift.authorization.data.User
import com.example.shift.databinding.ActivityMainBinding
import com.example.shift.utils.setupWithNavControllerAndOnSelectedListener
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), OnMenuItemSelectedListener, OnAuthorized {
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

        AuthorizationFragment.user = Gson().fromJson(
            getSharedPreferences(AuthorizationFragment.APP_PREFERENCES, MODE_PRIVATE).getString(
                AuthorizationFragment.APP_PREFERENCES_USER, ""
            ),
            User::class.java
        )

        tuneNavigation()
    }

    private fun tuneNavigation() {
        binding.navView.menu.clear()
        binding.buttonNavView.menu.clear()

        val header = binding.navView.getHeaderView(0)

        if (AuthorizationFragment.user == null) {
            header.findViewById<TextView>(R.id.titleTextView).text = "You're not authorized"
            binding.navView.inflateMenu(R.menu.drawer_menu_not_authorized)
            binding.buttonNavView.inflateMenu(R.menu.button_nav_menu_not_authorized)
            return
        }

        binding.navView.inflateMenu(R.menu.drawer_menu_authorized)
        binding.buttonNavView.inflateMenu(R.menu.button_nav_menu_authorized)
        header.findViewById<TextView>(R.id.titleTextView).text =
            "${AuthorizationFragment.user!!.name} ${AuthorizationFragment.user!!.surname}"
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
                    false
                }
                R.id.logOutButton -> {
                    val sharedPreferences =
                        getSharedPreferences(AuthorizationFragment.APP_PREFERENCES, MODE_PRIVATE)
                    sharedPreferences.edit().remove(AuthorizationFragment.APP_PREFERENCES_USER)
                        .apply()

                    AuthorizationFragment.user = null
                    tuneNavigation()
                    false
                }
                else -> false
            }
        )
    }

    override fun onAuthorized() {
        binding.buttonNavView.menu.performIdentifierAction(R.id.feedFragment, 0)
        tuneNavigation()
    }
}