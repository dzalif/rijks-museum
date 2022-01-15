package com.alif.rijksmuseum.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseViewModel
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.repository.UserRepository
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: UserRepository) : BaseViewModel() {

    private val _logout = MutableLiveData<ResultState<Boolean>>()
    val logout: LiveData<ResultState<Boolean>> get() = _logout

    fun logout() {
        setResultLogout(ResultState.Loading())
        try {
            setResultLogout(ResultState.HasData(true))
            repository.logout()
        } catch (e: Throwable) {
            when (e) {
                is IOException -> setResultLogout(ResultState.NoInternetConnection())
                is TimeoutException -> setResultLogout(ResultState.Error(R.string.timeout))
                else -> setResultLogout(ResultState.Error(R.string.unknown_error))
            }
        }
    }

    private fun setResultLogout(result: ResultState<Boolean>) {
        _logout.postValue(result)
    }

}