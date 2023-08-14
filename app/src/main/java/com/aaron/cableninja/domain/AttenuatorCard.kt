package com.aaron.cableninja.domain

/********************************************
 * class AttenuatorCard
 *      encapsulates all attenuator data
 *      for the UI to use
 ********************************************/
class AttenuatorCard(
        id: String,
        desc: String,
        footage: Int = 0,
        iscoax: Boolean = false,
        loss: Double = 0.0
) {
        private val _id = id
        private val _desc = desc
        private var _footage = footage
        private val _iscoax = iscoax
        private var _loss = loss

        fun id(): String { return _id }
        fun desc(): String { return _desc }
        fun footage(): Int { return _footage }
        fun setFootage(footage: Int) { _footage = footage }
        fun iscoax(): Boolean { return _iscoax }
        fun setLoss(loss: Double) { _loss = loss }
        fun getLoss(): Double { return _loss }
}

