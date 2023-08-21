package com.aaron.cableninja.data

import com.aaron.cableninja.domain.AttenuatorTag

data class AttenuatorData(
    val id: Int,
    val name: String,
    val tags: List<AttenuatorTag>
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
class Attenuator(name: String, tags: List<AttenuatorTag>,
                 iscoax: Boolean = false,
                 ispassive: Boolean = false,
                 isdrop: Boolean = false,
                 isplant: Boolean = false
    ) {
    private var _id = 0
    private val _data = AttenuatorData(_id, name, tags)
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
    fun isPassive(): Boolean { return _ispassive }
    fun isDrop(): Boolean { return _isdrop }
    fun isPlant(): Boolean { return _isplant }

    // get ID
    fun id(): Int { return _data.id }

    // get name
    fun name(): String { return _data.name }

    // get tag list
    fun tags(): List<AttenuatorTag> { return _data.tags }
    // get tag list as strings
    fun tagsToStrings(): List<String> {
        val strings = mutableListOf<String>()
        _data.tags.forEach {
            strings.add(it.string)
        }
        return strings
    }

    // get loss at frequency
    fun getLoss(freq: Int): Double? {
        return specs[freq]
    }
}

