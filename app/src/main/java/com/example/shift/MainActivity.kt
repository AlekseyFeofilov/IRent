package com.example.shift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.shift.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host = fragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment? ?: return
        val navController = host.navController
        //val navController = binding.navFragment.findNavController()

        binding.navView.setupWithNavController(navController)

        val appBarConfiguration =
            AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.buttonNavView.setupWithNavController(navController)


        /*binding.feedButton.setOnClickListener { navController.navigate(R.id.feedFragment) }
        binding.chatButton.setOnClickListener { navController.navigate(R.id.chatFragment) }
        binding.createCardButton.setOnClickListener { navController.navigate(R.id.createCardFragment) }
        binding.profileButton.setOnClickListener { navController.navigate(R.id.profileFragment) }*/
    }
}