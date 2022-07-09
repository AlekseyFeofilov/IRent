package com.example.shift.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.ui.NavigationUI
import com.example.shift.OnMenuItemSelectedListener
import com.google.android.material.navigation.NavigationBarView
import java.lang.ref.WeakReference

fun NavigationBarView.setupWithNavControllerAndOnSelectedListener(
    navController: NavController,
    onMenuItemSelectedListener: OnMenuItemSelectedListener
) {
    setupWithNavController(this, navController, onMenuItemSelectedListener)
}

private fun setupWithNavController(
    navigationBarView: NavigationBarView,
    navController: NavController,
    onMenuItemSelectedListener: OnMenuItemSelectedListener
) {
    navigationBarView.setOnItemSelectedListener { item ->
        onMenuItemSelectedListener.onMenuItemSelectedListener(item)

        NavigationUI.onNavDestinationSelected(
            item,
            navController
        )
    }
    val weakReference = WeakReference(navigationBarView)
    navController.addOnDestinationChangedListener(
        object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }
                view.menu.forEach { item ->
                    if (destination.matchDestination(item.itemId)) {
                        item.isChecked = true
                    }
                }
            }
        })
}

private fun NavDestination.matchDestination(@IdRes destId: Int): Boolean =
    hierarchy.any { it.id == destId }
