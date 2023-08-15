package com.aaron.cableninja.domain

/********************************************
 * class ManufacturerSpecs
 *      encapsulates all attenuator data
 *      including a map of manufacturer specs
 *          key = frequency
 *          value = loss @ 100'
 ********************************************/
class ManufacturerSpecs(
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
    val specs = mutableMapOf<Int, Double>()

    // Is this attenuator coax?
    fun iscoax() : Boolean { return _iscoax }

    // get ID string
    fun id(): String { return _id }

    // get Description string
    fun desc(): String { return _desc }

    // get loss at frequency
    fun getLoss(freq: Int): Double? {
        return specs[freq]
    }
}

class AttenuatorDataList {
    private var _list = mutableListOf<ManufacturerSpecs>()

    fun add(data: ManufacturerSpecs) {
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

