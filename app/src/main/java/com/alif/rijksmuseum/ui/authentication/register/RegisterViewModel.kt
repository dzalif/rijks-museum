package com.alif.rijksmuseum.ui.authentication.register

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alif.rijksmuseum.R
import com.alif.rijksmuseum.base.BaseViewModel
import com.alif.rijksmuseum.common.ResultState
import com.alif.rijksmuseum.repository.CheckBoxListener
import com.alif.rijksmuseum.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val repository: UserRepository) : BaseViewModel() {

    private val _register = MutableLiveData<ResultState<Boolean>>()
    val register: LiveData<ResultState<Boolean>> get() = _register

    var checkBoxListener: CheckBoxListener? = null

    var username = ObservableField<String>()
    var password = ObservableField<String>()

    val errorUsername = ObservableInt()
    val errorPassword = ObservableInt()
    private var errorCheckBox = ObservableInt()

    private val disposables = CompositeDisposable()

    fun validateRegister(isChecked: Boolean) {
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

        if (!isChecked) {
            checkBoxListener?.onChecked()
            setErrorCheckBox(R.string.please_agree_the_terms_and_conditions)
            return
        }

        setErrorCheckBox()
        register(username, password)
    }

    private fun register(username: String, password: String) {
        setResultRegister(ResultState.Loading())
        val disposable = repository.register(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setResultRegister(ResultState.HasData(true))
            }, {
                when (it) {
                    is IOException -> setResultRegister(ResultState.NoInternetConnection())
                    is TimeoutException -> setResultRegister(ResultState.Error(R.string.timeout))
                    else -> setResultRegister(ResultState.ErrorMessage(it.message!!))
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

    private fun setErrorCheckBox(errorMessageResId: Int = 0) {
        errorCheckBox.set(errorMessageResId)
    }

    private fun setResultRegister(result: ResultState<Boolean>) {
        _register.postValue(result)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}