package com.alif.rijksmuseum.ui.main

import android.annotation.SuppressLint
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
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.databinding.ActivityMainBinding
import com.alif.rijksmuseum.ui.authentication.login.FragmentCallback
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), FragmentCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.mainContent)

        getCurrentUser()

        initToolbar()
        drawerNavigation()
    }

    private fun drawerNavigation() {
        binding.contentNavigationDrawer.tvProfile.setOnClickListener {
            navController.navigate(R.id.profileFragment)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.contentNavigationDrawer.tvHome.setOnClickListener {
            navController.navigate(R.id.museumFragment)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentUser() {
        val user = firebaseAuth.currentUser
        binding.contentNavigationDrawer.tvUser.text = "Welcome \n${user?.email}"
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
                    showToolbar(true)
                    showToolbarBackArrow(false)
                }
                R.id.registerFragment -> {
                    showToolbar(true)
                    showToolbarBackArrow(true)
                }
                else -> {
                    showToolbar(true)
                    showMenu()
                    showToolbarBackArrow(true)
                }
            }
        }

    override fun onBackPressed() {
        when(navController.currentDestination?.id) {
            R.id.loginFragment -> finish()
        }
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
                when(navController.currentDestination?.id) {
                    R.id.detailMuseumFragment, R.id.registerFragment ->  navController.navigateUp()
                    else -> binding.drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
        }
        return true
    }

    override fun refreshMain() {
        getCurrentUser()
    }
}
