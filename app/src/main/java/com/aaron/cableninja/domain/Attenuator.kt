package com.aaron.cableninja.domain

/************************************************************
 * class Attenuator
 *      encapsulates all attenuator data
 *      including a map of manufacturer specs
 *          key = frequency
 *          value = loss @ 100'
 ************************************************************/
class Attenuator(id: String, tags: List<AttenuatorTag>,
                 iscoax: Boolean = false,
                 ispassive: Boolean = false,
                 isdrop: Boolean = false,
                 isplant: Boolean = false
    ) {
    private val _id = id
    private val _tags = tags
    private val _iscoax = iscoax
    private val _ispassive = ispassive
    private val _isdrop = isdrop
    private val _isplant = isplant

    // store manufacturer specs as k/v pairs
    //      key = frequency
    //      value = attenuation in dB per 100'
    val specs = mutableMapOf<Int, Double>()

    // Is this attenuator coax?
    fun isCoax() : Boolean { return _iscoax }
    // Is this attenuator passive?
    fun isPassive(): Boolean { return !_iscoax }
    fun isDrop(): Boolean { return _isdrop }
    fun isPlant(): Boolean { return _isplant }

    // get ID string
    fun id(): String { return _id }

    // get tag list
    fun tags(): List<AttenuatorTag> { return _tags }
    // get tag list as strings
    fun tagsToStrings(): List<String> {
        val strings = mutableListOf<String>()
        _tags.forEach {
            strings.add(it.string)
        }
        return strings
    }

    // get loss at frequency
    fun getLoss(freq: Int): Double? {
        return specs[freq]
    }
}

/************************************************************
 *  AttenuatorType
 *      enumeration for describing types of attenuators
 ************************************************************/
enum class AttenuatorType {
    COAX,
    PASSIVE,
    DROP,
    PLANT
}

