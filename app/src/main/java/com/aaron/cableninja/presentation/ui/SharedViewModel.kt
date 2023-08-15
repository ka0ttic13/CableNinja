package com.aaron.cableninja.presentation.ui

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
    fun addAttenuatorLength(footage: Int) {
        if (card != null)
            card!!.setFootage(footage)
    }

    // get attenuator length
    fun getAttenuatorLength() : Int {
        return card!!.footage()
    }

    var hasLoadedAddList by mutableStateOf(false)
        private set
    fun setHasLoadedAddList() { hasLoadedAddList = true }

    var hasListChanged by mutableStateOf(false)
        private set
    fun setHasListChanged() { hasListChanged = true}
}