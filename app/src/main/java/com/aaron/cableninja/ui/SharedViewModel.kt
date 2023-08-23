package com.aaron.cableninja.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aaron.cableninja.domain.AttenuatorCard

// ViewModel for sharing data between screens
class SharedViewModel : ViewModel() {
    // frequency slider value
    //      defaults to 1.218GHz
    var currentFreq by mutableStateOf(1218f)
        private set
    fun setFreq(freq: Float) { currentFreq = freq }

    // temperature slider value
    //      defaults to 68F (manufacturer spec default)
    var currentTemp by mutableStateOf(68f)
    fun setTemp(temp: Float) { currentTemp = temp }

    // start level value
    var currentStartLevel by mutableStateOf("")
    fun setStartLevel(level: String) { currentStartLevel = level }

    // total attenuation
    var totalAttenuation by mutableStateOf(0.0)
        private set
    fun setTotalAtten(total: Double) { totalAttenuation = total }

    // AttenuatorCard we can use to pass from AddScreen to MainScreen
    var card by mutableStateOf<AttenuatorCard?>(null)
        private set
    // set AttenuatorCard
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

    // get attenuator length
    fun getAttenuatorLength() : Int { return card!!.length() }

    // list change state
    var hasListChanged by mutableStateOf(false)
        private set
    fun setHasListChanged() { hasListChanged = true}

}