package com.example.api_integration

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var myRecyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progress)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        // Show the ProgressBar
        progressBar.visibility = View.VISIBLE
        myRecyclerView.visibility = View.GONE

        val retrofitData = retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<myData?> {
            override fun onResponse(call: Call<myData?>, response: Response<myData?>) {
                // Hide the ProgressBar and show the RecyclerView
                progressBar.visibility = View.GONE
                myRecyclerView.visibility = View.VISIBLE

                // If the API call is successful
                val dataList = response.body()?.data ?: emptyList()
                myAdapter = MyAdapter(this@MainActivity, dataList)
                myRecyclerView.adapter = myAdapter
                myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                Log.d("TAG: onResponse ", "onResponse: " + response.body())
            }

            override fun onFailure(call: Call<myData?>, t: Throwable) {
                // Hide the ProgressBar if the call fails
                progressBar.visibility = View.GONE

                Log.d("TAG: onFailure", "onFailure: " + t.message)
            }
        })
    }
}
