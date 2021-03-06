package com.alif.rijksmuseum.model.response


import com.alif.rijksmuseum.model.data.ArtObject
import com.google.gson.annotations.SerializedName

data class DetailMuseumResponse(
    @SerializedName("artObject")
    val artObject: ArtObject,
    @SerializedName("elapsedMilliseconds")
    val elapsedMilliseconds: Int?
)