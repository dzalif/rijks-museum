package com.alif.rijksmuseum.ui.authentication.login

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseViewModel
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: UserRepository) : BaseViewModel() {

    private val _login = MutableLiveData<ResultState<Boolean>>()
    val login: LiveData<ResultState<Boolean>> get() = _login

    var username = ObservableField<String>()
    var password = ObservableField<String>()

    val errorUsername = ObservableInt()
    val errorPassword = ObservableInt()

    private val disposables = CompositeDisposable()

    fun validateLogin() {
        val username = username.get()
        val password = password.get()

        if (username.isNullOrEmpty() && password.isNullOrEmpty()) {
            setErrorUsername(R.string.empty_username)
            setErrorPassword(R.string.empty_password)
            return
        }

        if (username.isNullOrEmpty()) {
            setErrorUsername(R.string.empty_username)
            return
        }

        setErrorUsername()

        if (password.isNullOrEmpty()) {
            setErrorPassword(R.string.empty_password)
            return
        }

        setErrorPassword()

        login(username, password)
    }

    private fun login(username: String, password: String) {
        setResultLogin(ResultState.Loading())
        val disposable = repository.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setResultLogin(ResultState.HasData(true))
            }, {
                when (it) {
                    is IOException -> setResultLogin(ResultState.NoInternetConnection())
                    is TimeoutException -> setResultLogin(ResultState.Error(R.string.timeout))
                    else -> setResultLogin(ResultState.ErrorMessage(it.message!!))
                }
            })
        disposables.add(disposable)
    }

    private fun setErrorUsername(errorMessageResId: Int = 0) {
        errorUsername.set(errorMessageResId)
    }

    private fun setErrorPassword(errorMessageResId: Int = 0) {
        errorPassword.set(errorMessageResId)
    }

    private fun setResultLogin(result: ResultState<Boolean>) {
        _login.postValue(result)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}