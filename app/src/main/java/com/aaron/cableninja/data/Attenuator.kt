package com.aaron.cableninja.data

data class AttenuatorData(
    val name: String,
    val tags: List<AttenuatorType>,
    val id: Int = 0
)

enum class AttenuatorType {
    COAX,
    PASSIVE,
    DROP,
    PLANT
}

/************************************************************
 * class Attenuator
 *      encapsulates all attenuator data
 *      including a map of manufacturer specs
 *          key = frequency
 *          value = loss @ 100'
 ************************************************************/
class Attenuator(name: String,
                 tags: List<AttenuatorType>,
                 iscoax: Boolean = false,
                 ispassive: Boolean = false,
                 isdrop: Boolean = false,
                 isplant: Boolean = false
    ) {
    private val _data = AttenuatorData(name, tags)
    private val _iscoax = iscoax
    private val _ispassive = ispassive
    private val _isdrop = isdrop
    private val _isplant = isplant

    // store manufacturer specs as k/v pairs
    //      key = frequency
    //      value = attenuation in dB per 100'
    var specs = mutableMapOf<Int, Double>()

    fun isCoax() : Boolean { return _iscoax }
    fun isPassive(): Boolean { return _ispassive }
    fun isDrop(): Boolean { return _isdrop }
    fun isPlant(): Boolean { return _isplant }

    fun name(): String { return _data.name }

    fun tags(): List<AttenuatorType> { return _data.tags }

    fun getLoss(frequency: Int): Double? {
        return specs[frequency]
    }
}

