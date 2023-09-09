package com.aaron.cableninja.ui.mainscreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.aaron.cableninja.ui.theme.LightGreen

const val TX_LOW_THRESHOLD = 30
const val TX_HIGH_THRESHOLD = 54
const val RX_LOW_THRESHOLD = -10
const val RX_HIGH_THRESHOLD = 11

class ThresholdColor(
    private val allPlant: Boolean,
    private val transmit: Boolean,
    private val result: Double
) {
    @Composable
    fun getColor(): Color {
        if (allPlant)
            return MaterialTheme.colorScheme.primary

        return if (transmit) {
            if ((result >= TX_LOW_THRESHOLD) && (result < TX_HIGH_THRESHOLD))
                LightGreen
            else
                Color.Red
        } else {
            if ((result >= RX_LOW_THRESHOLD) && (result < RX_HIGH_THRESHOLD))
                LightGreen
            else
                Color.Red
        }
    }
}
