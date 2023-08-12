package com.aaron.cableninja.model

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

    return loss68 * (1.0 + (0.0011 * (temp - 68)))
}

/***
 * Given a known loss on a known frequency (from manufacturer specs),
 * calculate an approximate loss for an undocumented frequency
 *
 ***/
fun getApproxLoss(
    knownLoss: Double,
    knownFreq: Double,
    unknownFreq: Double
) : Double {
    return knownLoss * sqrt(unknownFreq / knownFreq)
}
