package com.example.project_list

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.stations_row.view.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerStationList.setBackgroundColor(Color.BLUE)
        recyclerStationList.layoutManager = LinearLayoutManager(this)

        getApi();
    }

    private fun getApi() {
        var apiKey = "92432d86a1bd2a09a71f88ad41df06f6\n"
        var url =
            "https://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743,2988507,1850144,5344157,3128760,3117735,2267057,1857910,2800866,2992166,2507480,5815135,6167865&appid=" + apiKey

        //Create a request object

        val request = Request.Builder().url(url).build()

        //Create a client

        val client = OkHttpClient()

        //Use client object to work with request object

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // TODO("Not yet implemented")
                Log.i("JSON", "JSON HTTP CALL FAILED")
            }

            override fun onResponse(call: Call, response: Response) {
                // TODO("Not yet implemented")
                Log.i("JSON", "JSON HTTP CALL SUCCEEDED")
                val body = response?.body?.string()
                //  println("json loading" + body)
                //Log.i("JSON", body)

                //body


                var jsonBody = "{\"apiweather\": [" + body + "]}"
                Log.i("JSON", jsonBody)

                val gson = GsonBuilder().create()
                var weatherList = gson.fromJson(body, Weather::class.java)



                runOnUiThread {
                   recyclerStationList.adapter = StationListAdapter(weatherList.list)

                }
            }
        })

    }
}

class StationListAdapter(val apilist: List<WeatherStation>)
    :
        RecyclerView.Adapter<CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // TODO("Not yet implemented")
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.stations_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // TODO("Not yet implemented")

        holder.itemView.name.text = apilist[position].name

        holder?.data = apilist[position]
    }

    override fun getItemCount(): Int {
        ///  TODO("Not yet implemented")

        return apilist.size
    }


}

class CustomViewHolder(view: View, var data:WeatherStation?= null) : RecyclerView.ViewHolder(view) {
    companion object {
        val LOGCAT_CATEGORY = "JSON"
        val Country_temp = "Country_temp"
        val Country_feels_like = "Country_feels_like"
        val Country_humidity = "Country_humidity"
        val Country_pressure = "Country_pressur"
        val DETAIL_TITLE_KEY = "ActionBarTitle"

    }

    init {
        view.setOnClickListener {

            Log.i(LOGCAT_CATEGORY,"Recycler view Item has been clicked")
            Log.i(LOGCAT_CATEGORY, "Dt is " + data?.dt)

            val intent = Intent(view.context, RecyclerDetail::class.java)

            intent.putExtra(DETAIL_TITLE_KEY,"Details on " + data?.name)

            intent.putExtra(Country_temp, data?.main?.temp.toString())
            intent.putExtra(Country_feels_like, data?.main?.feels_like.toString())
            intent.putExtra(Country_humidity, data?.main?.humidity.toString())
            intent.putExtra(Country_pressure, data?.main?.pressure.toString())

            view.context.startActivity(intent)
        }

    }
}