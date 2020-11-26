package com.example.retrofitkotlin

import android.widget.Toast
import com.example.retrofitkotlin.ApiGetTestRecycle.my.MyService
import com.example.retrofitkotlin.ApiGetTestRecycle.my.country
import com.example.retrofitkotlin.ApiGetTestRecycle.my.countryData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.android.synthetic.main.about_country.*
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun GetCountryRussia() {

        var country: countryData? = null

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu/rest/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val api = retrofit.create(MyService::class.java)
        api.GetDataCountry("russia").enqueue(object :Callback<List<countryData>>{
            override fun onResponse(call: Call<List<countryData>>, response: Response<List<countryData>>) {
                //showData(response.body())
                country = response.body()?.first()


            }

            override fun onFailure(call: Call<List<countryData>>, t: Throwable) {

            }
        })

        Thread.sleep(1000)
        assertEquals("Russian Federation", country?.name)
        assertEquals("Europe", country?.region)
        assertEquals(146599183, country?.population)

    }

    @Test
    fun GetAllCountries() {
        var countries: List<country>? = null

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu/rest/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val api = retrofit.create(MyService::class.java)
        api.GetAllData().enqueue(object : Callback<List<country>> {
            override fun onResponse(call: Call<List<country>>, response: Response<List<country>>) {

                var countries = response.body()

                assertEquals(250, countries?.size)
            }

            override fun onFailure(call: Call<List<country>>, t: Throwable) {

            }
        })



    }
}