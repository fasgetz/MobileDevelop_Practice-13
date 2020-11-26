package com.example.retrofitkotlin.ApiGetTestRecycle.my

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyService {
    @GET("all?fields=name;capital")
    fun GetAllData(): Call<List<country>>

    @GET("name/{country}?fields=name;population;region")
    fun GetDataCountry(@Path("country") country: String): Call<List<countryData>>
}