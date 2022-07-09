package com.example.shift.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.customview.widget.Openable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.ui.NavigationUI
import com.example.shift.OnMenuItemSelectedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import java.lang.ref.WeakReference

fun NavigationView.setupWithNavControllerAndOnSelectedListener(
    navController: NavController,
    onMenuItemSelectedListener: OnMenuItemSelectedListener
) {
    setupWithNavController(this, navController, onMenuItemSelectedListener)
}

private fun setupWithNavController(
    navigationView: NavigationView,
    navController: NavController,
    onMenuItemSelectedListener: OnMenuItemSelectedListener
) {
    navigationView.setNavigationItemSelectedListener { item ->
        onMenuItemSelectedListener.onMenuItemSelectedListener(item)

        val handled = NavigationUI.onNavDestinationSelected(item, navController)
        if (handled) {
            val parent = navigationView.parent
            if (parent is Openable) {
                parent.close()
            } else {
                val bottomSheetBehavior = NavigationUI.findBottomSheetBehavior(navigationView)
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
        handled
    }
    val weakReference = WeakReference(navigationView)
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
                    item.isChecked = destination.matchDestination(item.itemId)
                }
            }
        })
}

private fun NavDestination.matchDestination(@IdRes destId: Int): Boolean =
    hierarchy.any { it.id == destId }