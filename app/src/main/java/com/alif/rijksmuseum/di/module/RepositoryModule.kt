package com.alif.rijksmuseum.di.module

import com.alif.rijksmuseum.api.ApiService
import com.alif.rijksmuseum.repository.FirebaseSource
import com.alif.rijksmuseum.repository.MuseumRepository
import com.alif.rijksmuseum.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(apiService: ApiService) = MuseumRepository(apiService)

    @Provides
    @Singleton
    fun provideUserRepository(firebase: FirebaseSource) = UserRepository(firebase)
}