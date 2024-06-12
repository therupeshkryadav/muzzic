package com.example.api_integration

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers(
        "X-RapidAPI-Key: 564350a0c7mshe6f1a601680bb35p1ef0d8jsn0edf48a344f6",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com"
    )
    @GET("search")
    fun getData(@Query("q") query: String): Call<myData>
}
