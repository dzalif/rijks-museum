package com.alif.basemvvm.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alif.basemvvm.di.factory.ViewModelFactory
import com.alif.basemvvm.ui.museum.MuseumViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MuseumViewModel::class)
    internal abstract fun providesMoviesViewModel(viewModel: MuseumViewModel) : ViewModel
}