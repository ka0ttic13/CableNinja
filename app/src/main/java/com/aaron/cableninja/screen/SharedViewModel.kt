package com.aaron.cableninja.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// ViewModel for sharing data between screens
class SharedViewModel : ViewModel() {
    var card by mutableStateOf<AttenuatorCard?>(null)
        private set

    fun addAttenuatorCard(newCard: AttenuatorCard) {
        card = newCard
    }
}