package com.aaron.cableninja.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("v1/forecast?current_weather=true")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ) : WeatherDTO
}

fun celsiusToFahrenheit(celsius: Double) : Double {
    return (celsius * (9/5)) + 32
}