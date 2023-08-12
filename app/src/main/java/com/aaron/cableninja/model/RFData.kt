package com.aaron.cableninja.model

// TODO - load from XML?
class RFData(id: String, desc: String, iscoax: Boolean) {
    private val _iscoax = iscoax
    private val _id = id
    private val _desc = desc

    // store attenuation data as k/v pairs
    // key = frequency
    // value = attenuation in dB @ frequency
    val dataMap = mutableMapOf<Int, Double>()

    // Is this attenuator coax?
    fun iscoax() : Boolean { return _iscoax }

    // get ID string
    fun id(): String { return _id }

    // get Description string
    fun desc(): String { return _desc }
}


