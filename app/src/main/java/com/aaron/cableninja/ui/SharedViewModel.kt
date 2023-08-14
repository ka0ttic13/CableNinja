package com.aaron.cableninja.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aaron.cableninja.model.AttenuatorCard

// ViewModel for sharing data between screens
class SharedViewModel : ViewModel() {
    // frequency slider value
    var currentFreq by mutableStateOf(1200f)
        private set
    fun setFreq(freq: Float) { currentFreq = freq }

    // temperature slider value
    var currentTemp by mutableStateOf(70f)
    fun setTemp(temp: Float) { currentTemp = temp }

    // clear main attenuator list
    var clearAttenuatorList by mutableStateOf(false)
        private set

    fun setClearListTrue() { clearAttenuatorList = true }
    fun setClearListFalse() { clearAttenuatorList = false }

    // total attenuation
    var totalAttenuation by mutableStateOf(0.0)
        private set
    fun setTotalAtten(total: Double) { totalAttenuation = total }

    // AttenuatorData we can use to pass from AddScreen to MainScreen
    var card by mutableStateOf<AttenuatorCard?>(null)
        private set
    // set AttenuatorData
    fun setAttenuatorCard(newCard: AttenuatorCard) {
        card = newCard
    }

    // Set attenuator length
    fun addAttenuatorLength(footage: Int) {
        if (card != null)
            card!!.setFootage(footage)
    }
    // get attenuator length
    fun getAttenuatorLength() : Int {
        return card!!.footage()
    }

    // Add Footage Dialog
    var isFootageDialogShown by mutableStateOf(false)
        private set
    fun onFootageAddClick() {
        isFootageDialogShown = true
    }
    fun onFootageDismissDialog() {
        isFootageDialogShown = false
    }

    var hasLoadedAddList by mutableStateOf(false)
        private set
    fun setHasLoadedAddList() { hasLoadedAddList = true }
}