package com.aaron.cableninja.model

import android.util.Log

class AttenuatorData(
    id: String,
    desc: String,
    iscoax: Boolean
) {
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

    // get loss at frequency
    fun getLoss(freq: Int): Double? {
        return dataMap[freq]
    }
}

class AttenuatorDataList {
    private var _list = mutableListOf<AttenuatorData>()

    fun add(data: AttenuatorData) {
        _list.add(data)
    }
    fun getLoss(id: String, freq: Int): Double? {
        _list.forEach() {
            if (it.id() == id)
                return it.getLoss(freq)
        }

        return 0.0
    }
}

