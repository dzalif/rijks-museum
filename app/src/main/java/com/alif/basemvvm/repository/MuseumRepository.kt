package com.alif.basemvvm.repository

import com.alif.basemvvm.api.ApiService
import com.alif.basemvvm.model.response.MuseumResponse
import javax.inject.Inject

class MuseumRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getListMuseum() : MuseumResponse {
        return apiService.getListMuseum()
    }
}