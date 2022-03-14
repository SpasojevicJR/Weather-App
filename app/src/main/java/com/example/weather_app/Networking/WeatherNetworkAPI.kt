package com.example.weather_app.Networking


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather_app.Model.WeatherData
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class WeatherAPI {

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeatherData(callback: (weatherData: MutableList<WeatherData>) -> Unit) {
        val thread = Thread {
            try {
                val BASE_URL =
                    "https://api.openweathermap.org/data/2.5/onecall?lat=44.79&lon=20.53&units=metric&exclude=daily,minutely&appid=0cc9bb25a8d5656a7f755baac8029c02"
                val url = URL(BASE_URL)
                val weatherDataArray: MutableList<WeatherData> = mutableListOf()
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    inputStream.bufferedReader().use {
                        it.lines().forEach { line ->
                            val weatherObject = JSONObject(line)
                            val listArray = weatherObject.getJSONArray("hourly")
                            for (i in 0 until listArray.length() - 1) {
                                val item = listArray.getJSONObject(i)
                                val timezone = weatherObject.getString("timezone")
                                val hour = item.getLong("dt")
                                val temperature = item.getDouble("temp")
                                val humidity = item.getString("humidity")
                                val windSpeed = item.getDouble("wind_speed")

                                val solution: Double = String.format("%.0f", temperature).toDouble()
                                val date = LocalDateTime.ofInstant(Instant.ofEpochSecond(hour), ZoneOffset.UTC)
                                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                                val output = formatter.format(date)
                                println("date $date")
                                val weatherDataDay = WeatherData(
                                    solution,
                                    "Humidity: $humidity%",
                                    windSpeed,
                                    output + "h",
                                    timezone
                                )
                                weatherDataArray.add(weatherDataDay)
                            }
                        }
                        callback(weatherDataArray)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

}