package com.aaron.cableninja.model

import android.util.Log
import kotlin.math.sqrt

/***
 * Adjust attenuation for given temperature
 *   Manufacturer data sheets list attenuation data for 68F
 *   Given a temperature and the amount of attenuation at a
 *   given frequency, we can calculate attenuation at any
 *   temperature using the following formula:
 *
 *   Attenuation at F = Atten. at 68F * (1+0.0011(t-68))
 ***/
fun adjustRFTemp(loss68: Double, temp: Int = 68) : Double {
    if (temp == 68)
        return loss68

    val result = loss68 * (1.0 + (0.0011 * (temp - 68)))
    Log.d("DEBUG", "adjustRFTemp(loss68 = $loss68, temp = $temp) = $result")
    return result
}

/***
 * getApproxLoss()
 *      Given a known loss on a known frequency (from manufacturer specs),
 *      calculate an approximate loss for an undocumented frequency
 *
 *      Uses formula from Cisco Broadband Data Book (pg 197)
 *          knownLoss * sqrt(unknownFreq / knownFreq)
 ***/
fun getApproxLoss(
    knownLoss: Double,
    knownFreq: Double,
    unknownFreq: Double
) : Double {

    Log.d(
        "DEBUG",
        "getApproxLoss() - knownLoss = $knownLoss, knownFreq = $knownFreq, unknownFreq = $unknownFreq")

    val result = (knownLoss * sqrt((unknownFreq / knownFreq)))
    Log.d("DEBUG", "getApproxLoss() returning $result")
    return result
}

/***
 * getCableLoss()
 *      given frequency, distance, and temp, calculate attenuation
 *
 ***/
fun getCableLoss(
    data: AttenuatorData?,
    freq: Int,
    distance: Int,
    temp: Int
): Double {
    var result = 0.0

    if (data == null) {
        Log.d("DEBUG", "getCableLoss() - AttenuatorData == NULL!")
        return -1.0
    }

    Log.d("DEBUG", "getCableLoss() - distance: $distance")
    Log.d("DEBUG", "getCableLoss() - temp: $temp")

    // If freq is an exact match to q frequency listed in manufacturer specs
    // go ahead and return the result
    data.dataMap.forEach() {
        if (it.key == freq) {
            Log.d("DEBUG", "getCableLoss() found key $freq")
            // if coax, re-calculate with distance and temperature
            if (data.iscoax())
                return (distance * adjustRFTemp(it.value, temp)) / 100
            else
                return it.value
        }
    }

    Log.d("DEBUG", "getCableLoss() DID NOT find key $freq...")

    // If we made it this far, the frequency set by the UI was not an exact match to one of
    // the frequencies in the manufacturer specs.  Get the approximate loss based on the closest
    // known frequency.
    var closestFreq = 1200

    // find closest frequency
    for (key in data.dataMap.keys) {
        if (key > freq) {
            Log.d("DEBUG", "getCableLoss() found closest frequency $key")
            closestFreq = key
            break
        }
    }

    // get approximate loss
    result = data.getLoss(closestFreq)?.let {
        getApproxLoss(it, closestFreq.toDouble(), freq.toDouble())
    }!!

    Log.d("DEBUG", "getCableLoss() after getApproxLoss() result = $result")

    // re-calculate with distance and temp if this is coax
    if (data.iscoax()) {
        result = (distance * adjustRFTemp(result, temp)) / 100
        Log.d("DEBUG", "getCableLoss() after distance/temp adjustment, result = $result")
    }
    else
        Log.d(
            "DEBUG",
            "getCableLoss() this is PASSIVE, skipping distance/temp adjustment, result = $result"
        )

    return result
}

