package com.aaron.cableninja.data

data class AttenuatorData(
    val name: String,
    val tags: List<AttenuatorType>,
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
    ) {
    private val _data = AttenuatorData(name, tags)

    // store manufacturer specs as k/v pairs
    //      key = frequency
    //      value = attenuation in dB per 100'
    var specs = mutableMapOf<Int, Double>()

    fun isCoax() : Boolean { return _data.tags.contains(AttenuatorType.COAX) }

    fun name(): String { return _data.name }

    fun tags(): List<AttenuatorType> { return _data.tags }

    fun getLoss(frequency: Int): Double? {
        return specs[frequency]
    }
}

