package com.alif.rijksmuseum.ui.museum.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseViewModel
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.model.data.ArtObject
import com.alif.rijksmuseum.repository.MuseumRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class DetailMuseumViewModel @Inject constructor(private val repository: MuseumRepository) : BaseViewModel() {
    private val _detail = MutableLiveData<ResultState<ArtObject>>()
    val detail: LiveData<ResultState<ArtObject>> get() = _detail

    fun getDetailMuseum(objectNumber: String) {
        setResultMuseum(ResultState.Loading())
        viewModelScope.launch {
            try {
                val response = repository.getDetailMuseum(objectNumber)
                val result = response.artObject
                setResultMuseum(ResultState.HasData(result))
            } catch (e: Throwable) {
                when (e) {
                    is IOException -> setResultMuseum(ResultState.NoInternetConnection())
                    is TimeoutException ->  setResultMuseum(ResultState.Error(R.string.timeout))
                    else -> setResultMuseum(ResultState.Error(R.string.unknown_error))
                }
            }
        }
    }

    private fun setResultMuseum(result: ResultState<ArtObject>) {
        _detail.postValue(result)
    }
}