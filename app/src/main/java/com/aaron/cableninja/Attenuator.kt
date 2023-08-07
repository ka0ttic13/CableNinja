package com.aaron.cableninja

data class Attenuator(val id: String, val desc: String) {
    private val _id = id
    private val _desc = desc

    // store attenuation data as k/v pairs
    // key = frequency
    // value = attenuation in dB @ frequency
    val RFdata = mutableMapOf<Int, Double>()

    // get ID string
    fun id(): String { return _id }

    // get Description string
    fun desc(): String { return _desc }
}


