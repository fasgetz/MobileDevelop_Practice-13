package com.example.retrofitkotlin.ApiGetTestRecycle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.ApiGetTestRecycle.my.CountryAdapter
import com.example.retrofitkotlin.ApiGetTestRecycle.my.MyService
import com.example.retrofitkotlin.ApiGetTestRecycle.my.country
import com.example.retrofitkotlin.R
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity3 : AppCompatActivity() {

    var countries: List<country>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        //RecyclerView.ItemAnimator.ItemHolderInfo
    }

    private fun showData(cuntries: List<country>?) {
        list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity3)
            adapter = CountryAdapter(applicationContext, cuntries!!)
        }
    }

    fun LoadCountries(view: View?) {
        runBlocking {
            launch(Dispatchers.Default) {
                try {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://restcountries.eu/rest/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .build()

                    val api = retrofit.create(MyService::class.java)
                    api.GetAllData().enqueue(object :Callback<List<country>>{
                        override fun onResponse(call: Call<List<country>>, response: Response<List<country>>) {
                            showData(response.body())
                            countries = response.body()

                            Toast.makeText(applicationContext, "${countries?.count().toString()} стран загружено", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<List<country>>, t: Throwable) {
                            Toast.makeText(applicationContext, "failed load", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                catch (ex: Exception) {
                    val myToast = Toast.makeText(applicationContext, ex.message, Toast.LENGTH_LONG)
                    myToast.show()
                }
            }
        }

    }

}