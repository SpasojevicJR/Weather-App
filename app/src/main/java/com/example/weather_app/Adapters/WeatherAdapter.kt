package com.example.weather_app.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.Model.WeatherData
import com.example.weather_app.R

class WeatherAdapter(val context: Context, val weatherDataSource: MutableList<WeatherData>, val itemClick: (WeatherData) -> Unit) : RecyclerView.Adapter<WeatherAdapter.Holder>() {

    inner class Holder(itemView: View, val itemClick: (WeatherData) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val temperature = itemView.findViewById<TextView>(R.id.textView)
        val humidity = itemView.findViewById<TextView>(R.id.humidity)
        val windSpeed = itemView.findViewById<TextView>(R.id.wind)
        val hour = itemView.findViewById<TextView>(R.id.time)
        val icon = itemView.findViewById<ImageView>(R.id.weatherIconView)
        val timezone = itemView.findViewById<TextView>(R.id.location)


        fun bindWeather(weatherDataSource: WeatherData) {
            val resID = context.resources.getIdentifier(
                weatherDataSource.icon,
                "drawable",
                context.packageName
            )
            timezone?.text = weatherDataSource.timezone
            temperature?.text = weatherDataSource.temperature.toString()
            humidity?.text = weatherDataSource.humidity
            windSpeed?.text = weatherDataSource.windSpeed.toString()
            hour?.text = weatherDataSource.hour.toString()
            icon?.setImageResource(resID)

            itemView.setOnClickListener { itemClick(weatherDataSource) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.weather_data, parent, false)
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindWeather(weatherDataSource[position])
    }

    override fun getItemCount(): Int {
        return weatherDataSource.count()
    }
}