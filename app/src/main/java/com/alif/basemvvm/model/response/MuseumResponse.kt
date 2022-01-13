package com.alif.basemvvm.model.response

import com.alif.basemvvm.model.data.ArtObject
import com.google.gson.annotations.SerializedName

data class MuseumResponse(
    @SerializedName("artObjects")
    val artObjects: List<ArtObject>,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("elapsedMilliseconds")
    val elapsedMilliseconds: Int?,
)