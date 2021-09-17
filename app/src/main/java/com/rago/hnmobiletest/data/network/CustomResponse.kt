package com.rago.hnmobiletest.data.network

import com.google.gson.annotations.SerializedName
import com.rago.hnmobiletest.data.model.Hit

data class CustomResponse(

    @SerializedName("hits")
    val hits: List<Hit>
)
