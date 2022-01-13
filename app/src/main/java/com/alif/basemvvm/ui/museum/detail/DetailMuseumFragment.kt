package com.alif.basemvvm.ui.museum.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.alif.basemvvm.R
import com.alif.basemvvm.base.BaseFragment
import com.alif.basemvvm.common.ResultState
import com.alif.basemvvm.databinding.DetailMuseumFragmentBinding


class DetailMuseumFragment : BaseFragment<DetailMuseumFragmentBinding, DetailMuseumViewModel>() {

    private lateinit var objectNumber: String
    private lateinit var toolbar: Toolbar

    override fun getViewModelClass(): Class<DetailMuseumViewModel> = DetailMuseumViewModel::class.java

    override fun getLayoutResourceId(): Int = R.layout.detail_museum_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = DetailMuseumFragmentArgs.fromBundle(requireArguments())
        objectNumber = arguments.objectNumber

        vm.getDetailMuseum(objectNumber)

        observeData()

    }

    private fun observeData() {
        vm.detail.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is ResultState.Loading -> {
                        hideData()
                        showLoading()
                    }
                    is ResultState.HasData -> {
                        hideLoading()
                        showData()
                        binding.data = it.data
                    }
                    is ResultState.NoData -> {
                        hideData()
                        hideLoading()
                        snackBar(resources.getString(R.string.empty_data))
                    }
                    is ResultState.Error -> {
                        hideData()
                        hideLoading()
                        snackBar(resources.getString(R.string.unknown_error))
                    }
                    is ResultState.NoInternetConnection -> {
                        hideData()
                        hideLoading()
                        snackBar(resources.getString(R.string.no_connection))
                    }
                }
            }
        })
    }

    private fun showLoading() {
        binding.showLoading = true
    }

    private fun hideLoading() {
        binding.showLoading = false
    }

    private fun showData() {
        binding.showData = true
    }

    private fun hideData() {
        binding.showData = false
    }
}