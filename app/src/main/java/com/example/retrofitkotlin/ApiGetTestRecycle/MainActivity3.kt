package com.example.retrofitkotlin.ApiGetTestRecycle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.Room.*
import com.example.retrofitkotlin.ApiGetTestRecycle.db.appDataBase
import com.example.retrofitkotlin.ApiGetTestRecycle.db.countryModel
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


        if (countries != null)
            showData(countries);
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        var count = countries?.count()
        if (count != null)
            savedInstanceState.putBoolean("load", true);

        super.onSaveInstanceState(savedInstanceState);

/*        val myToast = Toast.makeText(this@MainActivity3, "save", Toast.LENGTH_SHORT)
        myToast.show()*/
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        var load: Boolean = savedInstanceState.getBoolean("load")
        if (load == true)
            loadDataFromDb();
/*        val myToast = Toast.makeText(this@MainActivity3, "restore ${countries?.count()}", Toast.LENGTH_SHORT)
        myToast.show()*/

        //showData(countries);
/*        var list = students!!.map { i -> i.infoStudent }
        adapterList?.clear()
        adapterList?.addAll(list!!)*/
    }

    private fun showData(cuntries: List<country>?) {
        list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity3)
            adapter = CountryAdapter(applicationContext, cuntries!!)
        }

        countriesCount.setText(countries?.count().toString())
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

                            countries = response.body()

                            showData(countries)

                            Toast.makeText(applicationContext, "${countries?.count().toString()} стран загружено с сервера", Toast.LENGTH_SHORT).show()
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


    // Сохранение в бд
    fun SaveDb(view: View?) {


        // если страны загружены
        if (countries?.count() != null) {

            var countriesNew: ArrayList<countryModel>? = ArrayList<countryModel>()

            countries?.toMutableList()?.forEach {
                var model: countryModel = countryModel()
                model.capital = it.capital
                model.name = it.name
                countriesNew?.add(model)
            }


            runBlocking {
                launch(Dispatchers.Default) {
                    try {
                        val db = databaseBuilder(
                            applicationContext,
                            appDataBase::class.java, "appDataBase"
                        ).build()

                        var dao = db?.GetCountriesDao()

                        // Удаляем из базы данных старые сущности
                        dao?.removeAll()
                        dao?.insertAll(countriesNew?.toList())
                    }
                    catch (ex: Exception) {
                        val myToast = Toast.makeText(applicationContext, ex.message, Toast.LENGTH_LONG)
                        myToast.show()
                    }
                }

                Toast.makeText(applicationContext, "${countriesNew?.count().toString()} добавлено в БД", Toast.LENGTH_SHORT).show()
            }

        }
        else {
            Toast.makeText(applicationContext, "Страны не загружены для сохранения в бд", Toast.LENGTH_SHORT).show()
        }


    }


    fun loadDataFromDb() {
        // если страны не загружены, то загрузить
        if (countries?.count() == null) {
            var all: List<countryModel?>? = null


            runBlocking {
                launch(Dispatchers.Default) {
                    try {
                        val db = databaseBuilder(
                            applicationContext,
                            appDataBase::class.java, "appDataBase"
                        ).build()

                        var dao = db?.GetCountriesDao()

                        // загружаем все сущности из бд
                        all = dao?.all
                    }
                    catch (ex: Exception) {
                        val myToast = Toast.makeText(applicationContext, ex.message, Toast.LENGTH_LONG)
                        myToast.show()
                    }
                }

            }




            // После загрузки
            var countriesNew: ArrayList<country>? = ArrayList()


            all?.toMutableList()?.forEach {
                var model = country(it?.name.toString(), it?.capital.toString())
                countriesNew?.add(model)
            }

            countries = countriesNew?.toList()
            //Toast.makeText(applicationContext, "Стран загружено из базы данных ${countries?.count()}", Toast.LENGTH_SHORT).show()
            showData(countries)


        }
        else {
            Toast.makeText(applicationContext, "Страны уже загружены", Toast.LENGTH_SHORT).show()
        }

    }
    // Загрузка из бд
    fun LoadOnDb(view: View?) {

        loadDataFromDb();

    }

}