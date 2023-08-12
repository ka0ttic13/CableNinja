package com.aaron.cableninja.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aaron.cableninja.model.AttenuatorCard

// ViewModel for sharing data between screens
class SharedViewModel : ViewModel() {
    var clearAttenuatorList by mutableStateOf(false)
        private set

    fun setClearListTrue() { clearAttenuatorList = true }
    fun setClearListFalse() { clearAttenuatorList = false }

    var totalAttenuation by mutableStateOf(0.0)
        private set
    fun setTotalAtten(total: Double) { totalAttenuation = total }

    // AttenuatorData we can use to pass from AddScreen to MainScreen
    var data by mutableStateOf<AttenuatorCard?>(null)
        private set
    // set AttenuatorData
    fun addAttenuatorData(newData: AttenuatorCard) {
        data = newData
    }

    // Set attenuator length
    fun addAttenuatorLength(footage: Int) {
        if (data != null)
            data!!.setFootage(footage)
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