package com.example.project_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class RecyclerDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title = intent.getStringExtra(CustomViewHolder.DETAIL_TITLE_KEY)
        supportActionBar?.title = title

        countryTempView.text = intent.getStringExtra(CustomViewHolder.Country_temp)
        countryFeelsView.text = intent.getStringExtra(CustomViewHolder.Country_feels_like)
        countryHumidityView.text = intent.getStringExtra(CustomViewHolder.Country_humidity)
        countryPressureView.text = intent.getStringExtra(CustomViewHolder.Country_pressure)

    }
}