package com.alif.rijksmuseum.ui.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseFragment
import com.alif.rijksmuseum.databinding.SplashFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {

    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java
    override fun getLayoutResourceId(): Int = R.layout.splash_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(1000L)
            navigateToDestination()
        }
    }

    private fun navigateToDestination() {
        val user = firebaseAuth.currentUser

        if (user != null) {
            val action = SplashFragmentDirections.actionToMuseumFragment()
            findNavController().navigate(action)
        } else {
            val action = SplashFragmentDirections.actionToLoginFragment()
            findNavController().navigate(action)
        }
    }

}