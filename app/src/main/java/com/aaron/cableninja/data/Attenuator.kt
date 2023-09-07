package com.aaron.cableninja.data

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
class Attenuator(
    name: String,
    tags: List<AttenuatorType>
) {
    private val _name = name
    private val _tags = tags

    // store manufacturer specs as k/v pairs
    //      key = frequency
    //      value = attenuation in dB per 100'
    var specs = mutableMapOf<Int, Double>()

    fun isCoax() : Boolean { return _tags.contains(AttenuatorType.COAX) }

    fun name(): String { return _name }

    fun tags(): List<AttenuatorType> { return _tags }

    fun getLoss(frequency: Int): Double? {
        return specs[frequency]
    }

    fun doesMatchSearch(search: String, filters: List<AttenuatorType>) : Boolean {
        // if all parameters are empty then it matches
        if (search.isEmpty() && filters.isEmpty())
            return true

        if (search.isNotEmpty() && filters.isEmpty()) {
            if (_name.contains(search, ignoreCase = true))
                return true
        }

        if (search.isNotEmpty() && filters.isNotEmpty()) {
            var matches = true

            filters.forEach {
                if (!_tags.contains(it))
                    matches = false
            }

            if (!_name.contains(search, ignoreCase = true))
                matches = false

            return matches
        }
        // only filters
        else {
            var matches = true

            filters.forEach {
                if (!_tags.contains(it))
                    matches = false
            }

            return matches
        }
    }
}

