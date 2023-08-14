package com.aaron.cableninja.data.remote

import com.squareup.moshi.Json
data class WeatherDataDTO(
    @field:Json(name = "temperature")
    val temperature: Double
)