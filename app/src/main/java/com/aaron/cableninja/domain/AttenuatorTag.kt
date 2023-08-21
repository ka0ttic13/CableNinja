package com.aaron.cableninja.domain

import com.aaron.cableninja.data.AttenuatorType

/******************************************************************
 * AttenuatorTag
 *      Describe an attenuator
 *
 *      Each tag represents an AttenuatorType with an
 *      associated string
 ******************************************************************/
class AttenuatorTag(val tag: AttenuatorType, var string: String) {
    constructor(tag: AttenuatorType) : this(tag, string = "") {
        string = when (tag) {
            AttenuatorType.COAX     -> "coax"
            AttenuatorType.PASSIVE  -> "passive"
            AttenuatorType.DROP     -> "drop"
            AttenuatorType.PLANT    -> "plant"
        }
    }
    override fun toString(): String { return string }
}