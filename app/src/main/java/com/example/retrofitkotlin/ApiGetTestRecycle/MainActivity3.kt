package com.example.retrofitkotlin.ApiGetTestRecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitkotlin.ApiGetTestRecycle.my.MyService
import com.example.retrofitkotlin.R
import kotlinx.android.synthetic.main.activity_main3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu/rest/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(MyService::class.java)
        api.GetAllData().enqueue(object :Callback<List<country>>{
            override fun onResponse(call: Call<List<country>>, response: Response<List<country>>) {
                //showData(response.body())
                var data = response.body()



                Toast.makeText(applicationContext, data?.count().toString(), Toast.LENGTH_SHORT).show()
                //d("Ankan","OnResponse: ${response.body()!![0]?.phone}")
            }

            override fun onFailure(call: Call<List<country>>, t: Throwable) {
                Toast.makeText(applicationContext, "failed load", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun showData(cuntries: List<country>?) {
        recyclerView.apply {
            //layoutManager = LinearLayoutManager(this@MainActivity3)
            //adapter = UsersAdapter(cuntries!!)
        }
    }

}