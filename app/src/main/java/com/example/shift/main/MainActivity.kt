package com.example.shift.main

import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.cloudinary.android.MediaManager
import com.example.shift.*
import com.example.shift.companion.SharedPreferencesObject.Companion.APP_PREFERENCES
import com.example.shift.companion.SharedPreferencesObject.Companion.APP_PREFERENCES_ID
import com.example.shift.companion.SharedPreferencesObject.Companion.APP_PREFERENCES_USER
import com.example.shift.companion.SharedPreferencesObject.Companion.saveUser
import com.example.shift.companion.SharedPreferencesObject.Companion.user
import com.example.shift.databinding.ActivityMainBinding
import com.example.shift.extendes.setupWithNavControllerAndOnSelectedListener
import com.google.gson.Gson

class MainActivity : AppCompatActivity(),
    OnMenuItemSelectedListener,
    OnAuthorized,
    OnImageUpload,
    Settings,
    OnProgress,
    NavControllerActivity {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        user = getUser()
        setContentView(binding.root)

        val host = fragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment? ?: return
        val navController = host.navController
        val appBarConfiguration =
            AppBarConfiguration(navController.graph)

        binding.navView.setupWithNavControllerAndOnSelectedListener(navController, this)
        binding.buttonNavView.setupWithNavControllerAndOnSelectedListener(navController, this)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        tuneNavigation()
    }

    private fun tuneNavigation() {
        binding.navView.menu.clear()
        binding.buttonNavView.menu.clear()
        val header = binding.navView.getHeaderView(0)

        if (user == null) {
            header.findViewById<TextView>(R.id.titleTextView).text = getString(R.string.You_re_not_authorized)
            binding.navView.inflateMenu(R.menu.drawer_menu_not_authorized)
            binding.buttonNavView.inflateMenu(R.menu.button_nav_menu_not_authorized)
            return
        }

        binding.navView.inflateMenu(R.menu.drawer_menu_authorized)
        binding.buttonNavView.inflateMenu(R.menu.button_nav_menu_authorized)
        header.findViewById<TextView>(R.id.titleTextView).text = "${user!!.name} ${user!!.surname}"
        //header.findViewById<ImageView>(R.id.iconImageView).setImageURI("http://res.cloudinary.com/demo/image/upload/${user!!.icon}".toUri())
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
                    sharedPreferences.edit().remove(APP_PREFERENCES_USER).apply()
                    user = null
                    tuneNavigation()
                    false
                }
                else -> false
            }
        )
    }

    override fun onAuthorized() {
        binding.buttonNavView.menu.performIdentifierAction(R.id.feedFragment, 0)
        saveUser(sharedPreferences)
        tuneNavigation()
    }

    override fun onImageUpload(image: Uri): String {
        return MediaManager.get().upload(image).unsigned("sample_preset").dispatch()
    }

    override fun getSharedPreferences() = sharedPreferences

    override fun getID() = sharedPreferences.getLong(APP_PREFERENCES_ID, -1)

    override fun getUser(): User? =
        Gson().fromJson(sharedPreferences.getString(APP_PREFERENCES_USER, null), User::class.java)

    override fun onProgressStart() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onProgressEnd() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showMessage(message: String) {
        binding.messageTextView.text = message
    }
}