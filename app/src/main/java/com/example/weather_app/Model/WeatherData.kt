package com.example.weather_app.Model

import java.io.Serializable

class WeatherData (
    temperature : Double,
    humidity: String = "",
    windSpeed: Double,
    hour: String = "",
    timezone: String = ""
    ): Serializable {
    var temperature: Double = 0.0
    var humidity: String = ""
    var windSpeed: Double = 0.0
    var hour: String = ""
    var icon: String = ""
    var timezone: String = ""

    init {
        this.temperature = temperature
        this.humidity = humidity
        this.windSpeed = windSpeed
        this.hour = hour
        this.timezone = timezone

        val kf = 17
        val fk = 8

        if (hour <= kf.toString()) {
            icon = "ic_wi_day_sunny"
        } else if (hour >= kf.toString() && hour <= fk.toString()) {
            icon = "ic_wi_moon_waxing_6"
        }
    }
}