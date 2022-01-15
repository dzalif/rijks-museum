package com.alif.rijksmuseum.repository

import com.alif.rijksmuseum.api.ApiService
import com.alif.rijksmuseum.model.response.DetailMuseumResponse
import com.alif.rijksmuseum.model.response.MuseumResponse
import javax.inject.Inject

class MuseumRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getListMuseum() : MuseumResponse {
        return apiService.getListMuseum()
    }

    suspend fun getDetailMuseum(objectNumber: String) : DetailMuseumResponse {
        return apiService.getDetailMuseum(objectNumber)
    }
}