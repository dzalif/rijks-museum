package com.alif.basemvvm.api

import com.alif.basemvvm.model.response.MuseumResponse
import retrofit2.http.GET

interface ApiService {
    @GET("collection?key=kiHbgRgi&involvedMaker=Rembrandt+van+Rijn")
    suspend fun getListMuseum() : MuseumResponse
}