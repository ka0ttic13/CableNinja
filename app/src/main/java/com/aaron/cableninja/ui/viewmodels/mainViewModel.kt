package com.aaron.cableninja.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.ui.theme.coaxColor
import com.aaron.cableninja.ui.theme.dropColor
import com.aaron.cableninja.ui.theme.passiveColor
import com.aaron.cableninja.ui.theme.plantColor

const val DEFAULT_FREQ = 1218f
const val DEFAULT_TEMP = 68f

class mainViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    // map of attenuator tags to colors
    val attenuatorTags = mapOf(
        AttenuatorType.COAX to coaxColor,
        AttenuatorType.PASSIVE to passiveColor,
        AttenuatorType.DROP to dropColor,
        AttenuatorType.PLANT to plantColor
    )
//
//    private val _search = MutableStateFlow("")
//    val search = _search.asStateFlow()
//    fun onSearchChange(query: String) { _search.value = query }

//    private val _showList = MutableStateFlow(listOf<Attenuator>())
//    val showList = search
//        .combine(_showList) { text, attenuator ->
//
//        }

    // master map of name strings to manufacturer data
    var attenuatorList = mutableListOf<Attenuator>()
        private set

    // master list of RF data that has been added
    private var _attenuatorCardList =
        mutableListOf<AttenuatorCard?>(savedStateHandle["attenuatorCardList"])
    var attenuatorCardList: List<AttenuatorCard?> = _attenuatorCardList
    fun addAttenuatorCard(card: AttenuatorCard) {
        _attenuatorCardList.add(card)
        savedStateHandle["attenuatorCardList"] = _attenuatorCardList
    }
    fun removeAttenuatorCard(card: AttenuatorCard) {
        _attenuatorCardList.remove(card)
        savedStateHandle["attenuatorCardList"] = _attenuatorCardList
    }
    fun clearAttenuatorList() {
        _attenuatorCardList.clear()
        savedStateHandle["attenuatorCardList"] = _attenuatorCardList
    }

//    private var _currentFreq = MutableStateFlow(DEFAULT_FREQ)
//    val currentFreq = _currentFreq.asStateFlow()
//    fun onFreqChange(newFreq: Float) { _currentFreq.value = newFreq }

    var currentFreq by mutableStateOf(DEFAULT_FREQ)
        private set
    fun setFreq(freq: Float) { currentFreq = freq }

    var currentTemp by mutableStateOf(DEFAULT_TEMP)
        private set
    fun setTemp(temp: Float) { currentTemp = temp }

    var currentStartLevel by mutableStateOf("")
        private set
    fun setStartLevel(level: String) { currentStartLevel = level }

    var totalAttenuation by mutableStateOf(0.0)
        private set
    fun setTotalAtten(total: Double) { totalAttenuation = total }

    private var _card by mutableStateOf<AttenuatorCard?>(null)
    fun setAttenuatorCard(newCard: AttenuatorCard) { _card = newCard }
    fun getCurrentCard(): AttenuatorCard? { return _card }


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