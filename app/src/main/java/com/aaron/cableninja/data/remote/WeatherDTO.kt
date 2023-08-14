package com.aaron.cableninja.data.remote

import com.squareup.moshi.Json

data class WeatherDTO(
    @field:Json(name = "current_weather")
    val weatherData: WeatherDataDTO
)