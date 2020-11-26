package com.example.retrofitkotlin.ApiGetTestRecycle.my

import retrofit2.Call
import retrofit2.http.GET

interface MyService {
    @GET("all?fields=name;capital")
    fun GetAllData(): Call<List<country>>
}