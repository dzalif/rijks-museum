package com.alif.rijksmuseum.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alif.rijksmuseum.di.factory.ViewModelFactory
import com.alif.rijksmuseum.ui.authentication.login.LoginViewModel
import com.alif.rijksmuseum.ui.authentication.register.RegisterViewModel
import com.alif.rijksmuseum.ui.museum.MuseumViewModel
import com.alif.rijksmuseum.ui.museum.detail.DetailMuseumViewModel
import com.alif.rijksmuseum.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun providesSplashViewModel(viewModel: SplashViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MuseumViewModel::class)
    internal abstract fun providesMuseumViewModel(viewModel: MuseumViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailMuseumViewModel::class)
    internal abstract fun providesDetailMuseumViewModel(viewModel: DetailMuseumViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun providesLoginViewModel(viewModel: LoginViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    internal abstract fun providesRegisterViewModel(viewModel: RegisterViewModel) : ViewModel
}