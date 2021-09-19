package com.rago.hnmobiletest.data.network

import com.rago.hnmobiletest.data.model.CustomResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

// Urls para las peticiones
interface AlgoliaQuery {

    @Headers(
        "accept: application/json",
        "Content-type: application/json"
    )
    @GET("search_by_date?query=mobile")
    fun query(): Call<CustomResponse>

}