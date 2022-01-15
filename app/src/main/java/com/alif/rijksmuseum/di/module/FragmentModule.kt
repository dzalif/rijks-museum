package com.alif.rijksmuseum.di.module

import com.alif.rijksmuseum.ui.authentication.login.LoginFragment
import com.alif.rijksmuseum.ui.authentication.register.RegisterFragment
import com.alif.rijksmuseum.ui.museum.MuseumFragment
import com.alif.rijksmuseum.ui.museum.detail.DetailMuseumFragment
import com.alif.rijksmuseum.ui.profile.ProfileFragment
import com.alif.rijksmuseum.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributesSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributesMuseumFragment(): MuseumFragment

    @ContributesAndroidInjector
    abstract fun contributesDetailMuseumFragment(): DetailMuseumFragment

    @ContributesAndroidInjector
    abstract fun contributesLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributesRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun contributesProfileFragment(): ProfileFragment
}