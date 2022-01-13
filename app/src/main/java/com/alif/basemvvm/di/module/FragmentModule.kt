package com.alif.basemvvm.di.module

import com.alif.basemvvm.ui.museum.MuseumFragment
import com.alif.basemvvm.ui.museum.detail.DetailMuseumFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributesMuseumFragment(): MuseumFragment

    @ContributesAndroidInjector
    abstract fun contributesDetailMuseumFragment(): DetailMuseumFragment
}