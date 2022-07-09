package com.example.shift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.shift.card.CreateCardFragment
import com.example.shift.chat.ChatFragment
import com.example.shift.databinding.ActivityMainBinding
import com.example.shift.usercards.FavoritesFragment
import com.example.shift.usercards.OwnFragment
import com.example.shift.usercards.RentHistoryFragment
import com.example.shift.usercards.RentedFragment
import com.example.shift.wallet.WalletFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    //private var currentItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host = fragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment? ?: return
        val navController = host.navController
        val appBarConfiguration =
            AppBarConfiguration(navController.graph)

        binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.buttonNavView.setupWithNavController(navController)
        binding.buttonNavView.setOnItemSelectedListener(this)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(binding.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
            return
        }

        clearBackStack()
    }

    private fun selectItem(id: Int) {
        binding.buttonNavView.menu.forEach {
            if (it.itemId == id) {
                onNavigationItemSelected(it)
                return
            }
        }

        binding.navView.menu.forEach {
            if (it.itemId == id) {
                onNavigationItemSelected(it)
                return
            }
        }
    }

    private fun clearBackStack() {
        //if (fragmentManager.backStackEntryCount == 0) return

        while (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
        }

        binding.toolbar.title = "Feed"
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        //selectItem(R.id.feedFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment = when (item.itemId) {
            R.id.feedFragment -> {
                clearBackStack()
                null
            }
            R.id.chatFragment -> ChatFragment()
            R.id.createCardFragment -> CreateCardFragment()
            R.id.settingsFragment -> SettingsFragment()
            R.id.walletFragment -> WalletFragment()
            R.id.rentedFragment -> RentedFragment()
            R.id.ownFragment -> OwnFragment()
            R.id.rentHistoryFragment -> RentHistoryFragment()
            R.id.favoritesFragment -> FavoritesFragment()
            R.id.logOutButton -> {
                //todo: Log out (hide item and reset user data)
                null
            }
            R.id.sidebar -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                null
            }
            else -> null
        } ?: return true

        fragmentManager.beginTransaction().replace(R.id.navFragment, fragment)
            .addToBackStack(binding.toolbar.title.toString()).commit()

        /*if (currentItem != null) currentItem!!.isChecked = false
        item.isChecked = true
        currentItem = item*/

        binding.toolbar.title = item.titleCondensed
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return true
    }
}