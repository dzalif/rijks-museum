package com.alif.rijksmuseum.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseFragment
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.databinding.ProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>() {

    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun getViewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun getLayoutResourceId(): Int = R.layout.profile_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentUser()

        observeData()
    }

    private fun observeData() {
        vm.logout.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                        hideButton()
                    }
                    is ResultState.HasData -> {
                        hideLoading()
                        snackBar("Logout successfully")
                        navigateToLoginFragment()
                    }
                    is ResultState.ErrorMessage -> {
                        showButton()
                        hideLoading()
                        snackBar(it.errorMessage)
                    }
                    is ResultState.NoInternetConnection -> {
                        showButton()
                        hideLoading()
                        snackBar(resources.getString(R.string.no_connection))
                    }
                    else -> {}
                }
            }
        })
    }

    private fun navigateToLoginFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun hideButton() {
        binding.btnLogout.visibility = View.INVISIBLE
    }

    private fun showButton() {
        binding.btnLogout.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressbar.visibility = View.GONE
    }

    private fun getCurrentUser() {
        val user = firebaseAuth.currentUser
        binding.tvUserName.text = user?.email
    }

    fun onLogoutPressed() {
      vm.logout()
    }
}