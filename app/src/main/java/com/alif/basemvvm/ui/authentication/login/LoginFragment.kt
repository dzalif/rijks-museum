package com.alif.basemvvm.ui.authentication.login

import android.os.Bundle
import android.view.View
import com.alif.basemvvm.R
import com.alif.basemvvm.base.BaseFragment
import com.alif.basemvvm.databinding.LoginFragmentBinding

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getLayoutResourceId(): Int = R.layout.login_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}