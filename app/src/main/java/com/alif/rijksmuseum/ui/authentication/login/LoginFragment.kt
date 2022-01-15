package com.alif.rijksmuseum.ui.authentication.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseFragment
import com.alif.rijksmuseum.databinding.LoginFragmentBinding

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getLayoutResourceId(): Int = R.layout.login_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun onLoginPressed() {
        vm.validateLogin()
    }

    fun onRegisterPressed() {
        val action = LoginFragmentDirections.actionToRegisterFragment()
        findNavController().navigate(action)
    }

}