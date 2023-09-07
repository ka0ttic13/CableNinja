package com.aaron.cableninja.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.ui.theme.coaxColor
import com.aaron.cableninja.ui.theme.dropColor
import com.aaron.cableninja.ui.theme.passiveColor
import com.aaron.cableninja.ui.theme.plantColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

const val DEFAULT_FREQ = 1218f
const val DEFAULT_TEMP = 68f


// ViewModel for sharing data between screens
class SharedViewModel : ViewModel() {
    // map of attenuator tags to colors
    val attenuatorTags = mapOf(
        AttenuatorType.COAX to coaxColor,
        AttenuatorType.PASSIVE to passiveColor,
        AttenuatorType.DROP to dropColor,
        AttenuatorType.PLANT to plantColor
    )

//    private val _showList = MutableStateFlow(listOf<Attenuator>())
//
//    private val _search = MutableStateFlow("")
//    val search = _search.asStateFlow()

    // master map of name strings to manufacturer data
    var attenuatorList = mutableListOf<Attenuator>()
        private set

    // master list of RF data that has been added
    var attenuatorCardList = mutableListOf<AttenuatorCard>()
        private set

//    private var _currentFreq = MutableStateFlow(1218f)
//    val currentFreq = _currentFreq.asStateFlow()
    var currentFreq by mutableStateOf(DEFAULT_FREQ)

    fun setFreq(freq: Float) { currentFreq = freq }

    var currentTemp by mutableStateOf(DEFAULT_TEMP)
    fun setTemp(temp: Float) { currentTemp = temp }

    var currentStartLevel by mutableStateOf("")
    fun setStartLevel(level: String) { currentStartLevel = level }

    var totalAttenuation by mutableStateOf(0.0)
        private set
    fun setTotalAtten(total: Double) { totalAttenuation = total }

    var card by mutableStateOf<AttenuatorCard?>(null)
        private set
    fun setAttenuatorCard(newCard: AttenuatorCard) { card = newCard }
    fun addCurrentCardToList() {
        card?.let { attenuatorCardList.add(it) }
    }

    /* Set attenuator length
     * this is separate from setAttenuatorCard because
     *      - not every attenuator has a length
     *      - we don't get length input at the same time the AttenuatorCard is created
     */
    fun addAttenuatorLength(length: Int) { card?.setLength(length) }

    fun getAttenuatorLength() : Int { return card!!.length() }

    var hasListChanged by mutableStateOf(false)
        private set
    fun setHasListChanged() { hasListChanged = true}

    private var _filterList = mutableStateListOf<AttenuatorType>()
    var filterList: List<AttenuatorType> = _filterList
    fun addFilter(type: AttenuatorType) { _filterList.add(type) }
    fun removeFilter(type: AttenuatorType) { _filterList.remove(type) }
    fun clearFilters() { _filterList.clear() }

    init {
        loadRFdata(this)
    }
}