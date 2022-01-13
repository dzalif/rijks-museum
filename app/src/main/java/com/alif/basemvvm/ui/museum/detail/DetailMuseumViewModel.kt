package com.alif.basemvvm.ui.museum.detail

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alif.basemvvm.R
import com.alif.basemvvm.base.BaseViewModel
import com.alif.basemvvm.common.ResultState
import com.alif.basemvvm.model.data.ArtObject
import com.alif.basemvvm.repository.MuseumRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class DetailMuseumViewModel @Inject constructor(private val repository: MuseumRepository) : BaseViewModel() {
    private val _detail = MutableLiveData<ResultState<ArtObject>>()
    val detail: LiveData<ResultState<ArtObject>> get() = _detail
    
    var detailData = ObservableField<ArtObject>()

    fun getDetailMuseum(objectNumber: String) {
        setResultMuseum(ResultState.Loading())
        viewModelScope.launch {
            val response = repository.getDetailMuseum(objectNumber)
            val result = response.artObject
            detailData.set(response.artObject)
            try {
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