package com.example.retrofitkotlin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var users: ArrayList<PostModel>//? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        listView = findViewById(R.id.list)
//        listView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(RetrofitInterface::class.java)
        var call: Call<List<PostModel?>?>? = api.posts

        call?.enqueue(object : Callback<List<PostModel?>?> {
            override fun onResponse(
                call: Call<List<PostModel?>?>,
                response: Response<List<PostModel?>?>
            ) {
                var postlist: List<PostModel>? = response.body() as List<PostModel>
                var post = arrayOfNulls<String>(postlist!!.size)

                for (i in postlist!!.indices)
                    post[i] = postlist!![i]!!.title

                var adapter = ArrayAdapter<String>(applicationContext,android.R.layout.simple_dropdown_item_1line,post)
                list.adapter = adapter
//                var ad = PoastAdapter(this,postlist)



            }

            override fun onFailure(call: Call<List<PostModel?>?>, t: Throwable) {
                Toast.makeText(applicationContext, "OnFailure Called", Toast.LENGTH_SHORT).show()
            }
        })


    }
}