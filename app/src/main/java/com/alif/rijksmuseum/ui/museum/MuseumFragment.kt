package com.alif.rijksmuseum.ui.museum

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseFragment
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.databinding.FragmentMuseumBinding
import com.alif.rijksmuseum.model.data.ArtObject

class MuseumFragment : BaseFragment<FragmentMuseumBinding, MuseumViewModel>(), MuseumAdapter.OnMuseumPressedListener {
    private val adapter = MuseumAdapter(this)

    override fun getViewModelClass(): Class<MuseumViewModel> = MuseumViewModel::class.java

    override fun getLayoutResourceId(): Int = R.layout.fragment_museum

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        vm.museum.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is ResultState.Loading -> {
                        hideData()
                        showLoading()
                    }
                    is ResultState.HasData -> {
                        showData()
                        hideLoading()
                        refreshData(it.data)
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

    override fun onMuseumPressed(art: ArtObject, position: Int) {
        val action = MuseumFragmentDirections.actionToDetailMuseumFragment()
        action.objectNumber = art.objectNumber.toString()
        findNavController().navigate(action)
    }

    private fun initRecyclerView() {
        binding.rvMuseum.adapter = adapter
    }

    private fun refreshData(museums : List<ArtObject>) {
        adapter.submitList(museums)
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
