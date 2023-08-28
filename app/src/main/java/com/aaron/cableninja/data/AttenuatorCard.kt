package com.aaron.cableninja.data

/********************************************
 * class AttenuatorCard
 *      encapsulates all attenuator data
 *      for the UI to use
 ********************************************/
class AttenuatorCard(atten: Attenuator) {
        private var _atten = atten
        private val _name = _atten.name()
        private val _tags = _atten.tags()
        private var _iscoax = _atten.isCoax()
        private var _length = 0
        private var _loss = 0.0

        // secondary constructor without using pre-existing Attenuator object
        constructor(
                name: String,
                tags: List<AttenuatorType>,
                iscoax: Boolean,
                length: Int = 0,
                loss: Double = 0.0) : this(Attenuator(name, tags, iscoax)) {
                _length = length
                _loss = loss
        }

        fun name(): String { return _name }
        fun tags(): List<AttenuatorType> { return _tags }
        fun length(): Int { return _length }
        fun setLength(length: Int) { _length = length }
        fun isCoax(): Boolean { return _iscoax }
        fun setLoss(loss: Double) { _loss = loss }
        fun getLoss(): Double { return _loss }
}

