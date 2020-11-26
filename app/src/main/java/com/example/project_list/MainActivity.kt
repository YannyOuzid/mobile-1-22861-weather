package com.example.project_list

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
        recyclerStationList.setBackgroundColor(Color.YELLOW)
        recyclerStationList.layoutManager = LinearLayoutManager(this)
        getApi();
    }

    private fun getApi() {
        var apiKey = "92432d86a1bd2a09a71f88ad41df06f6\n"
        var url =
            "https://api.openweathermap.org/data/2.5/forecast?id=524901&appid=" + apiKey

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


                var jsonBody = "{\"list\": [" + body + "]}"
                Log.i("JSON", jsonBody)

                val gson = GsonBuilder().create()
                var stationList = gson.fromJson(jsonBody, Weather::class.java)

                // Log.i("JSON", stationList.stations[0].name)

                runOnUiThread {
                    recyclerStationList.adapter = StationListAdapter(stationList.list)

                }
            }
        })

    }
}

class StationListAdapter(val list: List<WeatherStation>)
    :
        RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // TODO("Not yet implemented")
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.stations_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // TODO("Not yet implemented")


        holder.itemView.cod.text = list[position].cod.toString()


    }

    override fun getItemCount(): Int {
        ///  TODO("Not yet implemented")

        return list.size
    }


}

class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}