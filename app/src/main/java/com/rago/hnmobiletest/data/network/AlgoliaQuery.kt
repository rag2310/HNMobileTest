package com.rago.hnmobiletest.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface AlgoliaQuery {

    @Headers(
        "accept: application/json",
        "Content-type: application/json"
    )
    @GET("search_by_date?query=mobile")
    fun query(): Call<CustomResponse>

}