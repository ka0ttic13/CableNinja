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

    /* store manufacturer specs as k/v pairs
     *      key = frequency
     *      value = attenuation in dB per 100'
     * */
    var specs = mutableMapOf<Int, Double>()

    fun isCoax() : Boolean { return _tags.contains(AttenuatorType.COAX) }

    fun name(): String { return _name }

    fun tags(): List<AttenuatorType> { return _tags }

    fun getLoss(frequency: Int): Double? {
        return specs[frequency]
    }

    fun doesMatchSearch(search: String, filters: List<AttenuatorType>) : Boolean {
        fun matchesFilters(f: List<AttenuatorType>): Boolean {
            var matches = true

            f.forEach {
                if (!_tags.contains(it))
                    matches = false
            }

            return matches
        }

        // if all parameters are empty then it matches
        return if (search.isEmpty() && filters.isEmpty())
            true

        // search and no filters
        else if (search.isNotEmpty() && filters.isEmpty())
            _name.contains(search, ignoreCase = true)

        // search and filters
        else if (search.isNotEmpty() && filters.isNotEmpty())
            matchesFilters(filters) &&
                    _name.contains(search, ignoreCase = true)
        // filters only
        else
            matchesFilters(filters)
    }
}

