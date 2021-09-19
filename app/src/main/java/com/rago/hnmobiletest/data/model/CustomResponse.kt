package com.rago.hnmobiletest.data.model

import com.google.gson.annotations.SerializedName

// Data class para el manejo del json de repuesta por parte del API
data class CustomResponse(

    @SerializedName("hits")
    val hits: List<Hit>
)
