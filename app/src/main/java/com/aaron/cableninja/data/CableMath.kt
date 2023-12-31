package com.aaron.cableninja.data

import android.util.Log
import kotlin.math.abs
import kotlin.math.sqrt

/***************************************************************************
 * Adjust attenuation for given temperature
 *   Manufacturer data sheets list attenuation data for 68F
 *   Given a temperature and the amount of attenuation at a
 *   given frequency, we can calculate attenuation at any
 *   temperature using the following formula:
 *
 *   Attenuation at F = Atten. at 68F * (1 + 0.0011(t - 68))
 **************************************************************************/
fun adjustRFTemp(loss68: Double, temp: Int = 68) : Double {
    if (temp == 68)
        return loss68

    return (loss68 * (1.0 + (0.0011 * (temp - 68))))
}

/***************************************************************************
 * getApproxLoss()
 *      Given a known loss on a known frequency (from manufacturer specs),
 *      calculate an approximate loss for an undocumented frequency
 *
 *      Uses formula from Cisco Broadband Data Book (pg 197)
 *          knownLoss * sqrt(unknownFreq / knownFreq)
 **************************************************************************/
fun getApproxLoss(
    knownLoss: Double,
    knownFreq: Double,
    unknownFreq: Double
) : Double {
    return (knownLoss * sqrt((unknownFreq / knownFreq)))
}

/***************************************************************************
 * getCableLoss()
 *      given an attenuator, frequency, distance, and temp,
 *      calculate attenuation
 **************************************************************************/
fun getCableLoss(
    data: Attenuator?,
    freq: Int,
    distance: Int,
    temp: Int
): Double {
    var result: Double

    if (data == null) {
        Log.d("DEBUG", "getCableLoss() - AttenuatorData == NULL!")
        return -1.0
    }

    Log.d("DEBUG", "getCableLoss() entering...")
    Log.d("DEBUG", "getCableLoss() - freq = $freq")
    Log.d("DEBUG", "getCableLoss() - distance = $distance")
    Log.d("DEBUG", "getCableLoss() - temp = $temp")

    // If freq is an exact match to q frequency listed in manufacturer specs
    // go ahead and return the result
    data.specs.forEach() {
        if (it.key == freq) {
            Log.d("DEBUG", "getCableLoss() found key $freq")
            // if coax, re-calculate with distance and temperature
            return if (data.isCoax())
                (distance * adjustRFTemp(it.value, temp)) / 100
            else
                it.value
        }
    }

    Log.d("DEBUG", "getCableLoss() DID NOT find key $freq...")

    // If we made it this far, the frequency set by the UI was not an exact match to one of
    // the frequencies in the manufacturer specs.  Get the approximate loss based on the closest
    // known frequency.

    val maxFreq = data.specs.keys.max()
    var closestFreq = maxFreq
    var min = Integer.MAX_VALUE

    Log.d("DEBUG", "getCableLoss(): maxFreq for ${data.name()} = $maxFreq")

    // find closest known frequency
    if (freq < maxFreq) {
        for (key in data.specs.keys) {
            val diff = abs(key - freq)

            if (diff < min) {
                min = diff
                closestFreq = key

                Log.d("DEBUG", "Found closest freq: $closestFreq")
            }
        }
    }

    // re-calculate with distance and temp if this is coax
    if (data.isCoax()) {
        // get approximate loss
        result = data.getLoss(closestFreq)?.let {
            getApproxLoss(it, closestFreq.toDouble(), freq.toDouble())
        }!!
        Log.d("DEBUG", "getCableLoss() after getApproxLoss() result = $result")

        result = (distance * adjustRFTemp(result, temp)) / 100
        Log.d("DEBUG", "getCableLoss() after distance/temp adjustment, result = $result")
    }
    else {
        result = data.getLoss(closestFreq)!!

        Log.d("DEBUG", "getCableLoss() after passive Attenuator::getLoss($closestFreq), result = $result")
    }

    return result
}

fun isNumeric(toCheck: String): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return toCheck.matches(regex)
}