package com.aaron.cableninja.data

/*********************************************************************************
 * class AttenuatorCard
 *      encapsulates all attenuator data for the UI to use
 *********************************************************************************/
class AttenuatorCard(atten: Attenuator) {
        private var _atten = atten
        private var _length = 0
        private var _loss = 0.0

        // secondary constructor for initializing when we do not have an
        // Attenuator object but have the data
        constructor(
                name: String,
                tags: List<AttenuatorType>,
                length: Int = 0,
                loss: Double = 0.0) : this(Attenuator(name, tags)) {
                _length = length
                _loss = loss
        }

        fun getAttenuator() : Attenuator { return _atten }
        fun name(): String { return _atten.name() }
        fun tags(): List<AttenuatorType> { return _atten.tags() }
        fun length(): Int { return _length }
        fun setLength(length: Int) { _length = length }
        fun isCoax(): Boolean { return _atten.isCoax() }
        fun setLoss(loss: Double) { _loss = loss }
        fun getLoss(): Double { return _loss }
}

