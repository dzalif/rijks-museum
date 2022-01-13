package com.alif.basemvvm.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.alif.basemvvm.R
import com.alif.basemvvm.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.mainContent)

        initToolbar()
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener(navigationListener)
    }

    private val navigationListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            invalidateOptionsMenu()
            when (destination.id) {
                R.id.splashFragment -> {
                    showToolbar(false)
                }
                R.id.detailMuseumFragment -> {
                    showToolbar(true)
                    showToolbarBackArrow(true)
                }
                R.id.loginFragment -> {
                    showToolbar(false)
                    showToolbarBackArrow(true)
                }
                else -> {
                    showToolbar(true)
                    showMenu()
                    showToolbarBackArrow(true)
                }
            }
        }

    override fun onSupportNavigateUp(): Boolean {
        when (navController.currentDestination?.id) {
            R.id.detailMuseumFragment -> navController.navigateUp()
        }
        return super.onSupportNavigateUp()
    }

    private fun showMenu() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)
    }

    private fun showToolbarBackArrow(shouldShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(shouldShow)
    }

    private fun showToolbar(shouldShow: Boolean) {
        if (shouldShow) toolbar.visibility = View.VISIBLE else toolbar.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (navController.currentDestination?.id == R.id.detailMuseumFragment) {
                   navController.navigateUp()
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
        }
        return true
    }
}
