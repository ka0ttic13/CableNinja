package com.aaron.cableninja.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aaron.cableninja.domain.AttenuatorCard

// ViewModel for sharing data between screens
class SharedViewModel : ViewModel() {
    // frequency slider value
    //      defaults to 1.2GHz
    var currentFreq by mutableStateOf(1200f)
        private set
    fun setFreq(freq: Float) { currentFreq = freq }

    // temperature slider value
    //      defaults to 68F (manufacturer spec default)
    var currentTemp by mutableStateOf(68f)
    fun setTemp(temp: Float) { currentTemp = temp }

    var currentStartLevel by mutableStateOf("")
    fun setStartLevel(level: String) { currentStartLevel = level }

    // clear main attenuator list
    var clearAttenuatorList by mutableStateOf(false)
        private set
    fun setClearList(value: Boolean) { clearAttenuatorList = value }

    // total attenuation
    var totalAttenuation by mutableStateOf(0.0)
        private set
    fun setTotalAtten(total: Double) { totalAttenuation = total }

    // AttenuatorData we can use to pass from AddScreen to MainScreen
    var card by mutableStateOf<AttenuatorCard?>(null)
        private set
    // set AttenuatorData
    fun setAttenuatorCard(newCard: AttenuatorCard) { card = newCard }

    // Set attenuator length
    // this is separate from setAttenuatorCard because
    //      - not every attenuator has a length
    //      - we don't get length input at the same time the AttenuatorCard is created
    fun addAttenuatorLength(footage: Int) {
        if (card != null)
            card!!.setLength(footage)
    }

    // get attenuator length
    fun getAttenuatorLength() : Int {
        return card!!.length()
    }

    // have we loaded the AddScreen list yet? state
    var hasLoadedAddList by mutableStateOf(false)
        private set
    fun setHasLoadedAddList() { hasLoadedAddList = true }

    // list change state
    var hasListChanged by mutableStateOf(false)
        private set
    fun setHasListChanged() { hasListChanged = true}

}