package com.aaron.cableninja.screen

data class AttenuatorCardData(
        val title: String,
        val subtitle: String,
        val feet: Int = 0,
        val coax: Boolean = false
)
{
        val iscoax = coax
        val id = title
        val desc = subtitle
        var footage = feet
}

class AttenuatorCard(data: AttenuatorCardData) {
        private val _card = data
        private val _loss: Double = 0.0

        fun id(): String { return _card.id }
        fun desc(): String { return _card.desc }
        fun footage(): Int { return _card.footage }

        fun iscoax(): Boolean { return _card.iscoax }

        fun loss(): Double { return _loss }
}

