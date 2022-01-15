package com.alif.rijksmuseum.ui.authentication.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseFragment
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.databinding.LoginFragmentBinding

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    private lateinit var fragmentCallback: FragmentCallback

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getLayoutResourceId(): Int = R.layout.login_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    private fun observeData() {
        vm.login.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                        hideButton()
                    }
                    is ResultState.HasData -> {
                        hideLoading()
                        clearEditText()
                        refreshMain()
                        snackBar("Login successfully")
                        navigateToHomeFragment()
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

    private fun refreshMain() {
        fragmentCallback.refreshMain()
    }

    private fun hideButton() {
        binding.btnLogin.visibility = View.INVISIBLE
    }

    private fun showButton() {
        binding.btnLogin.visibility = View.VISIBLE
    }

    private fun navigateToHomeFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToMuseumFragment()
        findNavController().navigate(action)
    }

    fun showLoading() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressbar.visibility = View.GONE
    }

    private fun clearEditText() {
        binding.etUsername.text?.clear()
        binding.etPassword.text?.clear()
    }

    fun onLoginPressed() {
        vm.validateLogin()
    }

    fun onRegisterPressed() {
        val action = LoginFragmentDirections.actionToRegisterFragment()
        findNavController().navigate(action)
    }

}