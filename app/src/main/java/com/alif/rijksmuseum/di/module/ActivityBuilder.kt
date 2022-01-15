package com.alif.rijksmuseum.di.module

import com.alif.rijksmuseum.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}