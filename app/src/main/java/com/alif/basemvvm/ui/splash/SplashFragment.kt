package com.alif.basemvvm.ui.splash

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alif.basemvvm.R
import com.alif.basemvvm.base.BaseFragment
import com.alif.basemvvm.databinding.SplashFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {

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
        val action = SplashFragmentDirections.actionToMuseumFragment()
        findNavController().navigate(action)
    }

}