package com.aaron.cableninja.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// ViewModel for sharing data between screens
class SharedViewModel : ViewModel() {
    var clearAttenuatorList by mutableStateOf(false)
        private set

    fun setClearListTrue() {
        clearAttenuatorList = true
    }

    fun setClearListFalse() {
        clearAttenuatorList = false
    }

    var totalAttenuation by mutableStateOf(0.0)
        private set

    fun setTotal(total: Double) {
        totalAttenuation = total
    }

    // AttenuatorCard we can use to pass from AddScreen to MainScreen
    var card by mutableStateOf<AttenuatorCardData?>(null)
        private set
    // set AttenuatorCard
    fun addAttenuatorCard(newCard: AttenuatorCardData) {
        card = newCard
    }

    // Set attenuator length
    fun addAttenuatorLength(footage: Int) {
        if (card != null)
            card!!.footage = footage
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
}