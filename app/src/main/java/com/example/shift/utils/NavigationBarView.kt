package com.example.shift.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationBarView
import java.lang.ref.WeakReference

fun NavigationBarView.setupWithNavControllerAndDrawerLayout(
    navController: NavController,
    drawerLayout: DrawerLayout,
    openSidebarItemId: Int
) {
    setupWithNavController(this, navController, drawerLayout, openSidebarItemId)
}

private fun setupWithNavController(
    navigationBarView: NavigationBarView,
    navController: NavController,
    drawerLayout: DrawerLayout,
    openSidebarItemId: Int
) {
    navigationBarView.setOnItemSelectedListener { item ->
        navigationBarView.menu.setGroupCheckable(0, true, true)

        if (item.itemId == openSidebarItemId) {
            drawerLayout.open()
        } else {
            drawerLayout.close()
        }

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
