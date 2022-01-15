package com.alif.rijksmuseum.api

import com.alif.rijksmuseum.model.response.DetailMuseumResponse
import com.alif.rijksmuseum.model.response.MuseumResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("collection?key=kiHbgRgi&involvedMaker=Rembrandt+van+Rijn")
    suspend fun getListMuseum() : MuseumResponse

    @GET("collection/{objectNum}?key=kiHbgRgi")
    suspend fun getDetailMuseum(@Path("objectNum") objectNumber: String) : DetailMuseumResponse
}