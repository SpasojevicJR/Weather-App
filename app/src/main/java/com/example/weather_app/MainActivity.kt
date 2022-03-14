package com.example.weather_app


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.Adapters.WeatherAdapter
import com.example.weather_app.Model.WeatherData
import com.example.weather_app.Networking.WeatherAPI

class MainActivity : AppCompatActivity() {
    private var locationManager: LocationManager? = null
    private lateinit var weatherAdapter: WeatherAdapter
    private var weatherDataSource: MutableList<WeatherData> = mutableListOf()
    private var recyclerView: RecyclerView? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLocation()

        WeatherAPI().getWeatherData {
            if (!it.isEmpty()) {
                weatherDataSource.addAll(it)
                runOnUiThread {
                    setupRecyclerView()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        weatherAdapter = WeatherAdapter(this, weatherDataSource) { weatherDataDay ->
            val weatherIntent = Intent(this, MainActivity::class.java).apply {
                putExtra(WEATHERINFO, weatherDataDay)
            }
            startActivity(weatherIntent)
        }

        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView?.adapter = weatherAdapter
    }

    private fun getLocation() {
        try {
            // Request location updates
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0f,
                locationListener
            )
        } catch (ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        @SuppressLint("SetTextI18n")
        override fun onLocationChanged(location: Location) {
            println(":" + location.latitude + "" + location.longitude)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}

const val WEATHERINFO = "com.example.weatherApp.MESSAGE"