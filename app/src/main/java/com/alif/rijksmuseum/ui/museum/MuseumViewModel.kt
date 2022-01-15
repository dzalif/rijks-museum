package com.alif.rijksmuseum.ui.museum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseViewModel
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.model.data.ArtObject
import com.alif.rijksmuseum.repository.MuseumRepository
import kotlinx.coroutines.launch
import timber.log.Timber
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
            try {
                val response = repository.getListMuseum()
                val result = response.artObjects
                if (result.isEmpty()) {
                    setResultMuseum(ResultState.NoData())
                    return@launch
                }
                setResultMuseum(ResultState.HasData(result))
            } catch (e: Throwable) {
                Timber.v("Error message ${e.message}")
                when (e) {
                    is IOException -> setResultMuseum(ResultState.NoInternetConnection())
                    is TimeoutException -> setResultMuseum(ResultState.Error(R.string.timeout))
                    else -> setResultMuseum(ResultState.Error(R.string.unknown_error))
                }
            }
        }
    }

    private fun setResultMuseum(result: ResultState<List<ArtObject>>) {
        _museum.postValue(result)
    }
}
