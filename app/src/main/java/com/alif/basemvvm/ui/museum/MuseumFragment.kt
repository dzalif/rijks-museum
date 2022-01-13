package com.alif.basemvvm.ui.museum

import android.os.Bundle
import android.view.View
import com.alif.basemvvm.R
import com.alif.basemvvm.base.BaseFragment
import com.alif.basemvvm.common.ResultState
import com.alif.basemvvm.databinding.FragmentMuseumBinding
import com.alif.basemvvm.model.data.ArtObject

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
                        showLeagues()
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
        snackBar(art.objectNumber.toString())
    }

    private fun initRecyclerView() {
        binding.rvMovies.adapter = adapter
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

    private fun showLeagues() {
        binding.showData = true
    }

    private fun hideData() {
        binding.showData = false
    }
}
