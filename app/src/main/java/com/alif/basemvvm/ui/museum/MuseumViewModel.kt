package com.alif.basemvvm.ui.museum

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

class MuseumViewModel @Inject constructor(private val repository: MuseumRepository) : BaseViewModel() {
    private val _museum = MutableLiveData<ResultState<List<ArtObject>>>()
    val museum: LiveData<ResultState<List<ArtObject>>> get() = _museum

    init {
        getListMuseum()
    }

    private fun getListMuseum() {
        setResultMuseum(ResultState.Loading())
        viewModelScope.launch {
            val response = repository.getListMuseum()
            val result = response.artObjects
            try {
                if (result.isEmpty()) {
                    setResultMuseum(ResultState.NoData())
                    return@launch
                }
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

    private fun setResultMuseum(result: ResultState<List<ArtObject>>) {
        _museum.postValue(result)
    }
}
