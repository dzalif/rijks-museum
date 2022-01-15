package com.alif.rijksmuseum.ui.authentication.register

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseFragment
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.databinding.RegisterFragmentBinding
import com.alif.rijksmuseum.repository.CheckBoxListener

class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>(), CheckBoxListener {

    override fun getViewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getLayoutResourceId(): Int = R.layout.register_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.checkBoxListener = this

        observeData()
    }

    private fun observeData() {
        vm.register.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                    }
                    is ResultState.HasData -> {
                        hideLoading()
                        clearEditText()
                        navigateToLoginFragment()
                    }
                    is ResultState.ErrorMessage -> {
                        hideLoading()
                        snackBar(it.errorMessage)
                    }
                    is ResultState.NoInternetConnection -> {
                        hideLoading()
                        snackBar(resources.getString(R.string.no_connection))
                    }
                    else -> {}
                }
            }
        })
    }

    fun showLoading() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressbar.visibility = View.GONE
    }

    fun onRegisterPressed() {
        val isChecked = binding.checkBox.isChecked
        vm.validateRegister(isChecked)
    }

    private fun navigateToLoginFragment() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun clearEditText() {
        binding.etUsername.text?.clear()
        binding.etPassword.text?.clear()
    }

    override fun onChecked() {
        binding.errorCheckBox.visibility = View.VISIBLE
    }
}