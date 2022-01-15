package com.alif.rijksmuseum.ui.authentication.login

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseViewModel
import com.alif.rijksmuseum.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: UserRepository) : BaseViewModel() {

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
//        authListener?.onStarted()
//
//        val disposable = repository.login(username, password)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                authListener?.onSuccess()
//            }, {
//                authListener?.onFailure(it.message!!)
//            })
//        disposables.add(disposable)
    }

    private fun setErrorUsername(errorMessageResId: Int = 0) {
        errorUsername.set(errorMessageResId)
    }

    private fun setErrorPassword(errorMessageResId: Int = 0) {
        errorPassword.set(errorMessageResId)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}