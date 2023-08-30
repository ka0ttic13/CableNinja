package com.aaron.cableninja.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.ui.loadRFdata
import com.aaron.cableninja.ui.theme.coaxColor
import com.aaron.cableninja.ui.theme.dropColor
import com.aaron.cableninja.ui.theme.passiveColor
import com.aaron.cableninja.ui.theme.plantColor

// ViewModel for sharing data between screens
class SharedViewModel : ViewModel() {
    // map of attenuator tags to colors
    var attenuatorTags = mutableMapOf<AttenuatorType, Color>()
        private set

    // master map of ID strings to manufacturer data
    var attenuatorMap = mutableMapOf<String, Attenuator>()
        private set

    // master list of RF data that has been added
    var attenuatorCardList = mutableListOf<AttenuatorCard>()
        private set

    init {
        attenuatorTags[AttenuatorType.COAX] = coaxColor
        attenuatorTags[AttenuatorType.PASSIVE] = passiveColor
        attenuatorTags[AttenuatorType.DROP] = dropColor
        attenuatorTags[AttenuatorType.PLANT] = plantColor

        loadRFdata(this)
    }


    var currentFreq by mutableStateOf(1218f)
        private set
    fun setFreq(freq: Float) { currentFreq = freq }

    var currentTemp by mutableStateOf(68f)
    fun setTemp(temp: Float) { currentTemp = temp }

    var currentStartLevel by mutableStateOf("")
    fun setStartLevel(level: String) { currentStartLevel = level }

    var totalAttenuation by mutableStateOf(0.0)
        private set
    fun setTotalAtten(total: Double) { totalAttenuation = total }

    var card by mutableStateOf<AttenuatorCard?>(null)
        private set
    fun setAttenuatorCard(newCard: AttenuatorCard) { card = newCard }

    /* Set attenuator length
     * this is separate from setAttenuatorCard because
     *      - not every attenuator has a length
     *      - we don't get length input at the same time the AttenuatorCard is created
     */
    fun addAttenuatorLength(footage: Int) {
        if (card != null)
            card!!.setLength(footage)
    }

    fun getAttenuatorLength() : Int { return card!!.length() }

    var hasListChanged by mutableStateOf(false)
        private set
    fun setHasListChanged() { hasListChanged = true}

    private var _filterList = mutableStateListOf<AttenuatorType>()
    var filterList: List<AttenuatorType> = _filterList
    fun addFilter(type: AttenuatorType) { _filterList.add(type) }
    fun removeFilter(type: AttenuatorType) { _filterList.remove(type) }
    fun clearFilters() { _filterList.clear() }
}