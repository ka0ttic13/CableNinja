package com.aaron.cableninja.ui

import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.data.AttenuatorTag
import com.aaron.cableninja.data.AttenuatorType

fun loadRFdata() {
    val coax    = AttenuatorTag(AttenuatorType.COAX)
    val passive = AttenuatorTag(AttenuatorType.PASSIVE)
    val drop    = AttenuatorTag(AttenuatorType.DROP)
    val plant   = AttenuatorTag(AttenuatorType.PLANT)

    // CommScope RG-59
    val RG59 = Attenuator(name = "RG59", tags = listOf(coax, drop),
        iscoax = true, ispassive = false, isdrop = true, isplant = false)
    RG59.specs[5] = 0.86
    RG59.specs[55] = 2.05
    RG59.specs[83] = 2.45
    RG59.specs[187] = 3.6
    RG59.specs[211] = 3.8
    RG59.specs[250] = 4.1
    RG59.specs[300] = 4.45
    RG59.specs[350] = 4.8
    RG59.specs[400] = 5.1
    RG59.specs[450] = 5.4
    RG59.specs[500] = 5.7
    RG59.specs[550] = 5.95
    RG59.specs[600] = 6.2
    RG59.specs[750] = 6.97
    RG59.specs[865] = 7.52
    RG59.specs[1000] = 8.12
    MainActivity.attenuatorMap[RG59.name()] = RG59

    // Amphenol RG6
    val RG6 = Attenuator(name = "RG6", tags = listOf(coax, drop),
        iscoax = true, ispassive = false, isdrop = true, isplant = false)
    RG6.specs[5] = 0.58
    RG6.specs[55] = 1.6
    RG6.specs[211] = 3.05
    RG6.specs[250] = 3.3
    RG6.specs[270] = 3.37
    RG6.specs[300] = 3.55
    RG6.specs[330] = 3.74
    RG6.specs[350] = 3.85
    RG6.specs[400] = 4.15
    RG6.specs[450] = 4.4
    RG6.specs[500] = 4.66
    RG6.specs[550] = 4.9
    RG6.specs[600] = 5.1
    RG6.specs[750] = 5.65
    RG6.specs[870] = 6.11
    RG6.specs[1000] = 6.55
    RG6.specs[1100] = 6.87
    RG6.specs[1200] = 7.18
    RG6.specs[1218] = 7.21
    MainActivity.attenuatorMap[RG6.name()] = RG6

    // Amphenol RG11
    val RG11 = Attenuator(name = "RG11", tags = listOf(coax, drop, plant),
        iscoax = true, ispassive = false, isdrop = true, isplant = true)
    RG11.specs[5] = .38
    RG11.specs[55] = 0.96
    RG11.specs[211] = 1.9
    RG11.specs[250] = 2.05
    RG11.specs[270] = 2.13
    RG11.specs[300] = 2.25
    RG11.specs[330] = 2.35
    RG11.specs[350] = 2.42
    RG11.specs[400] = 2.6
    RG11.specs[450] = 2.75
    RG11.specs[500] = 2.9
    RG11.specs[550] = 3.04
    RG11.specs[600] = 3.18
    RG11.specs[750] = 3.65
    RG11.specs[870] = 4.06
    RG11.specs[1000] = 4.35
    RG11.specs[1100] = 4.54
    RG11.specs[1200] = 4.71
    RG11.specs[1218] = 4.92
    MainActivity.attenuatorMap[RG11.name()] = RG11

    // Antronix 2-Way / 3-Way Low
    val TwoWay = Attenuator(name = "2 Way / 3 Way Low Leg", tags = listOf(passive, drop),
        iscoax = false, ispassive = true, isdrop = true, isplant = false)
    TwoWay.specs[5] = 3.5
    TwoWay.specs[55] = 3.5
    TwoWay.specs[211] = 3.7
    TwoWay.specs[250] = 3.7
    TwoWay.specs[400] = 3.7
    TwoWay.specs[550] = 3.8
    TwoWay.specs[750] = 3.8
    TwoWay.specs[870] = 4.2
    TwoWay.specs[1000] = 4.2
    TwoWay.specs[1100] = 4.6
    TwoWay.specs[1200] = 4.6
    MainActivity.attenuatorMap[TwoWay.name()] = TwoWay

    // Antronix Balanced 3-Way
    val ThreeWayBalanced = Attenuator(name = "3 Way Balanced", tags = listOf(passive, drop),
        iscoax = false, ispassive = true, isdrop = true, isplant = false)
    ThreeWayBalanced.specs[5] = 5.8
    ThreeWayBalanced.specs[55] = 5.8
    ThreeWayBalanced.specs[211] = 5.9
    ThreeWayBalanced.specs[250] = 6.0
    ThreeWayBalanced.specs[400] = 6.0
    ThreeWayBalanced.specs[550] = 6.1
    ThreeWayBalanced.specs[750] = 6.2
    ThreeWayBalanced.specs[870] = 6.8
    ThreeWayBalanced.specs[1000] = 6.8
    ThreeWayBalanced.specs[1100] = 7.5
    ThreeWayBalanced.specs[1200] = 7.5
    MainActivity.attenuatorMap[ThreeWayBalanced.name()] = ThreeWayBalanced

    // Antronix 2-Way
    val FourWay = Attenuator(name = "4 Way / 3 Way High Leg", tags = listOf(passive, drop),
        iscoax = false, ispassive = true, isdrop = true, isplant = false)
    FourWay.specs[5] = 7.0
    FourWay.specs[55] = 7.2
    FourWay.specs[211] = 7.2
    FourWay.specs[250] = 7.4
    FourWay.specs[550] = 7.4
    FourWay.specs[750] = 7.6
    FourWay.specs[870] = 8.5
    FourWay.specs[1000] = 8.5
    FourWay.specs[1100] = 8.9
    FourWay.specs[1200] = 8.9
    MainActivity.attenuatorMap[FourWay.name()] = FourWay

    // Antronix 8-Way
    val EightWay = Attenuator(name = "8 Way", tags = listOf(passive, drop),
        iscoax = false, ispassive = true, isdrop = true, isplant = false)
    EightWay.specs[5] = 11.2
    EightWay.specs[55] = 11.0
    EightWay.specs[211] = 11.2
    EightWay.specs[250] = 11.5
    EightWay.specs[550] = 11.5
    EightWay.specs[750] = 12.0
    EightWay.specs[870] = 12.5
    EightWay.specs[1000] = 12.5
    EightWay.specs[1100] = 12.7
    EightWay.specs[1200] = 12.9
    MainActivity.attenuatorMap[EightWay.name()] = EightWay

    // CommScope P3 500
    val P3500 = Attenuator(name = "0.500 P3", tags = listOf(coax, plant),
        iscoax = true, ispassive = false, isdrop = false, isplant = true)
    P3500.specs[5] = 0.16
    P3500.specs[55] = 0.54
    P3500.specs[85] = 0.68
    P3500.specs[204] = 1.07
    P3500.specs[211] = 1.09
    P3500.specs[250] = 1.2
    P3500.specs[300] = 1.31
    P3500.specs[350] = 1.43
    P3500.specs[400] = 1.53
    P3500.specs[450] = 1.63
    P3500.specs[500] = 1.73
    P3500.specs[550] = 1.82
    P3500.specs[600] = 1.92
    P3500.specs[750] = 2.16
    P3500.specs[865] = 2.34
    P3500.specs[1002] = 2.54
    P3500.specs[1218] = 2.83
    MainActivity.attenuatorMap[P3500.name()] = P3500

    // CommScope P3 625
    val P3625 = Attenuator(name = "0.625 P3", tags = listOf(coax, plant),
        iscoax = true, ispassive = false, isdrop = false, isplant = true)
    P3625.specs[5] = 0.13
    P3625.specs[55] = 0.45
    P3625.specs[85] = 0.56
    P3625.specs[204] = 0.89
    P3625.specs[211] = 0.92
    P3625.specs[250] = 1.0
    P3625.specs[300] = 1.08
    P3625.specs[350] = 1.18
    P3625.specs[400] = 1.27
    P3625.specs[450] = 1.35
    P3625.specs[500] = 1.43
    P3625.specs[550] = 1.5
    P3625.specs[600] = 1.58
    P3625.specs[750] = 1.78
    P3625.specs[865] = 1.93
    P3625.specs[1002] = 2.11
    P3625.specs[1218] = 2.32
    MainActivity.attenuatorMap[P3625.name()] = P3625

    // CommScope P3 750
    val P3750 = Attenuator(name = "0.750 P3", tags = listOf(coax, plant),
        iscoax = true, ispassive = false, isdrop = false, isplant = true)
    P3750.specs[5] = 0.11
    P3750.specs[55] = 0.37
    P3750.specs[85] = 0.46
    P3750.specs[204] = 0.72
    P3750.specs[211] = 0.74
    P3750.specs[250] = 0.81
    P3750.specs[300] = 0.89
    P3750.specs[350] = 0.97
    P3750.specs[400] = 1.05
    P3750.specs[450] = 1.12
    P3750.specs[500] = 1.18
    P3750.specs[550] = 1.24
    P3750.specs[600] = 1.31
    P3750.specs[750] = 1.48
    P3750.specs[865] = 1.61
    P3750.specs[1002] = 1.74
    P3750.specs[1218] = 1.95
    MainActivity.attenuatorMap[P3750.name()] = P3750

    // CommScope P3 875
    val P3875 = Attenuator(name = "0.875 P3", tags = listOf(coax, plant),
        iscoax = true, ispassive = false, isdrop = false, isplant = true)
    P3875.specs[5] = 0.09
    P3875.specs[55] = 0.33
    P3875.specs[85] = 0.4
    P3875.specs[204] = 0.63
    P3875.specs[211] = 0.66
    P3875.specs[250] = 0.72
    P3875.specs[300] = 0.78
    P3875.specs[350] = 0.84
    P3875.specs[400] = 0.91
    P3875.specs[450] = 0.97
    P3875.specs[500] = 1.03
    P3875.specs[550] = 1.08
    P3875.specs[600] = 1.14
    P3875.specs[750] = 1.29
    P3875.specs[865] = 1.41
    P3875.specs[1002] = 1.53
    P3875.specs[1218] = 1.7
    MainActivity.attenuatorMap[P3875.name()] = P3875

    // CommScope 540QR
    val QR540 = Attenuator(name = "0.540 QR", tags = listOf(coax, plant),
        iscoax = true, ispassive = false, isdrop = false, isplant = true)
    QR540.specs[5] = 0.14
    QR540.specs[55] = 0.47
    QR540.specs[85] = 0.59
    QR540.specs[204] = 0.93
    QR540.specs[211] = 0.95
    QR540.specs[250] = 1.03
    QR540.specs[300] = 1.13
    QR540.specs[350] = 1.23
    QR540.specs[400] = 1.32
    QR540.specs[450] = 1.4
    QR540.specs[500] = 1.49
    QR540.specs[550] = 1.56
    QR540.specs[600] = 1.64
    QR540.specs[750] = 1.85
    QR540.specs[865] = 2.0
    QR540.specs[1002] = 2.17
    QR540.specs[1218] = 2.41
    MainActivity.attenuatorMap[QR540.name()] = QR540

    // CommScope 715QR (my favorite to splice)
    val QR715 = Attenuator(name = "0.715 QR", tags = listOf(coax, plant),
        iscoax = true, ispassive = false, isdrop = false, isplant = true)
    QR715.specs[5] = 0.11
    QR715.specs[55] = 0.37
    QR715.specs[85] = 0.46
    QR715.specs[204] = 0.73
    QR715.specs[211] = 0.74
    QR715.specs[250] = 0.81
    QR715.specs[300] = 0.89
    QR715.specs[350] = 0.97
    QR715.specs[400] = 1.05
    QR715.specs[450] = 1.12
    QR715.specs[500] = 1.19
    QR715.specs[550] = 1.25
    QR715.specs[600] = 1.31
    QR715.specs[750] = 1.49
    QR715.specs[865] = 1.62
    QR715.specs[1002] = 1.75
    QR715.specs[1218] = 1.96
    MainActivity.attenuatorMap[QR715.name()] = QR715

    // CommScope 860QR
    val QR860 = Attenuator(name = "0.860 QR", tags = listOf(coax, plant),
        iscoax = true, ispassive = false, isdrop = false, isplant = true)
    QR860.specs[5] = 0.09
    QR860.specs[55] = 0.32
    QR860.specs[85] = 0.4
    QR860.specs[204] = 0.63
    QR860.specs[211] = 0.64
    QR860.specs[250] = 0.7
    QR860.specs[300] = 0.76
    QR860.specs[350] = 0.83
    QR860.specs[400] = 0.88
    QR860.specs[450] = 0.95
    QR860.specs[500] = 1.0
    QR860.specs[550] = 1.06
    QR860.specs[600] = 1.1
    QR860.specs[750] = 1.24
    QR860.specs[865] = 1.33
    QR860.specs[1000] = 1.44
    QR860.specs[1002] = 1.45
    QR860.specs[1218] = 1.61
    MainActivity.attenuatorMap[QR860.name()] = QR860

    // CommScope FFT 29Q 2 port tap
    val CS29Q2 = Attenuator(name = "FFT 29Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS29Q2.specs[5] = 0.3
    CS29Q2.specs[10] = 0.2
    CS29Q2.specs[50] = 0.2
    CS29Q2.specs[100] = 0.4
    CS29Q2.specs[450] = 0.5
    CS29Q2.specs[550] = 0.6
    CS29Q2.specs[750] = 0.7
    CS29Q2.specs[870] = 0.9
    CS29Q2.specs[1000] = 1.1
    CS29Q2.specs[1218] = 1.5
    MainActivity.attenuatorMap[CS29Q2.name()] = CS29Q2

    // CommScope FFT 29Q 4 port tap
    val CS29Q4 = Attenuator(name = "FFT 29Q 4 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS29Q4.specs[5] = 0.4
    CS29Q4.specs[10] = 0.3
    CS29Q4.specs[50] = 0.3
    CS29Q4.specs[100] = 0.5
    CS29Q4.specs[450] = 0.7
    CS29Q4.specs[550] = 0.8
    CS29Q4.specs[750] = 0.9
    CS29Q4.specs[870] = 1.1
    CS29Q4.specs[1000] = 1.2
    CS29Q4.specs[1218] = 1.7
    MainActivity.attenuatorMap[CS29Q4.name()] = CS29Q4

    // CommScope FFT 29Q 8 port tap
    val CS29Q8 = Attenuator(name = "FFT 29Q 8 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS29Q8.specs[5] = 0.4
    CS29Q8.specs[10] = 0.3
    CS29Q8.specs[50] = 0.3
    CS29Q8.specs[100] = 0.5
    CS29Q8.specs[450] = 0.7
    CS29Q8.specs[550] = 0.8
    CS29Q8.specs[750] = 0.9
    CS29Q8.specs[870] = 1.0
    CS29Q8.specs[1000] = 1.2
    CS29Q8.specs[1218] = 1.8
    MainActivity.attenuatorMap[CS29Q8.name()] = CS29Q8

    // CommScope FFT 26Q 2 port tap
    val CS26Q2 = Attenuator(name = "FFT 26Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS26Q2.specs[5] = 0.4
    CS26Q2.specs[10] = 0.3
    CS26Q2.specs[50] = 0.3
    CS26Q2.specs[100] = 0.5
    CS26Q2.specs[450] = 0.7
    CS26Q2.specs[550] = 0.8
    CS26Q2.specs[750] = 0.9
    CS26Q2.specs[870] = 1.0
    CS26Q2.specs[1000] = 1.2
    CS26Q2.specs[1218] = 1.6
    MainActivity.attenuatorMap[CS26Q2.name()] = CS26Q2

    // CommScope FFT 26Q 4 port tap
    val CS26Q4 = Attenuator(name = "FFT 26Q 4 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS26Q4.specs[5] = 0.4
    CS26Q4.specs[10] = 0.3
    CS26Q4.specs[50] = 0.3
    CS26Q4.specs[100] = 0.5
    CS26Q4.specs[450] = 0.7
    CS26Q4.specs[550] = 0.7
    CS26Q4.specs[750] = 0.8
    CS26Q4.specs[870] = 1.0
    CS26Q4.specs[1000] = 1.1
    CS26Q4.specs[1218] = 1.5
    MainActivity.attenuatorMap[CS26Q4.name()] = CS26Q4

    // CommScope FFT 26Q 8 port tap
    val CS26Q8 = Attenuator(name = "FFT 26Q 8 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS26Q8.specs[5] = 0.6
    CS26Q8.specs[10] = 0.5
    CS26Q8.specs[50] = 0.5
    CS26Q8.specs[100] = 0.7
    CS26Q8.specs[450] = 0.8
    CS26Q8.specs[550] = 1.0
    CS26Q8.specs[750] = 1.1
    CS26Q8.specs[870] = 1.2
    CS26Q8.specs[1000] = 1.3
    CS26Q8.specs[1218] = 1.7
    MainActivity.attenuatorMap[CS26Q8.name()] = CS26Q8

    // CommScope FFT 23Q 2 port tap
    val CS23Q2 = Attenuator(name = "FFT 23Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS23Q2.specs[5] = 0.4
    CS23Q2.specs[10] = 0.3
    CS23Q2.specs[50] = 0.3
    CS23Q2.specs[100] = 0.5
    CS23Q2.specs[450] = 0.7
    CS23Q2.specs[550] = 0.8
    CS23Q2.specs[750] = 0.9
    CS23Q2.specs[870] = 1.0
    CS23Q2.specs[1000] = 1.2
    CS23Q2.specs[1218] = 1.7
    MainActivity.attenuatorMap[CS23Q2.name()] = CS23Q2

    // CommScope FFT 23Q 4 port tap
    val CS23Q4 = Attenuator(name = "FFT 23Q 4 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS23Q4.specs[5] = 0.5
    CS23Q4.specs[10] = 0.4
    CS23Q4.specs[50] = 0.4
    CS23Q4.specs[100] = 0.6
    CS23Q4.specs[450] = 0.8
    CS23Q4.specs[550] = 0.9
    CS23Q4.specs[750] = 1.0
    CS23Q4.specs[870] = 1.1
    CS23Q4.specs[1000] = 1.2
    CS23Q4.specs[1218] = 1.6
    MainActivity.attenuatorMap[CS23Q4.name()] = CS23Q4

    // CommScope FFT 23Q 2 port tap
    val CS23Q8 = Attenuator(name = "FFT 23Q 8 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS23Q8.specs[5] = 0.9
    CS23Q8.specs[10] = 0.8
    CS23Q8.specs[50] = 0.7
    CS23Q8.specs[100] = 0.9
    CS23Q8.specs[450] = 1.2
    CS23Q8.specs[550] = 1.3
    CS23Q8.specs[750] = 1.4
    CS23Q8.specs[870] = 1.5
    CS23Q8.specs[1000] = 1.7
    CS23Q8.specs[1218] = 2.1
    MainActivity.attenuatorMap[CS23Q8.name()] = CS23Q8

    // CommScope FFT 20Q 2 port tap
    val CS20Q2 = Attenuator(name = "FFT 20Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS20Q2.specs[5] = 0.5
    CS20Q2.specs[10] = 0.4
    CS20Q2.specs[50] = 0.4
    CS20Q2.specs[100] = 0.6
    CS20Q2.specs[450] = 0.8
    CS20Q2.specs[550] = 0.9
    CS20Q2.specs[750] = 1.0
    CS20Q2.specs[870] = 1.1
    CS20Q2.specs[1000] = 1.3
    CS20Q2.specs[1218] = 1.6
    MainActivity.attenuatorMap[CS20Q2.name()] = CS20Q2

    // CommScope FFT 20Q 4 port tap
    val CS20Q4 = Attenuator(name = "FFT 20Q 4 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS20Q4.specs[5] = 0.7
    CS20Q4.specs[10] = 0.6
    CS20Q4.specs[50] = 0.5
    CS20Q4.specs[100] = 0.8
    CS20Q4.specs[450] = 1.9
    CS20Q4.specs[550] = 1.0
    CS20Q4.specs[750] = 1.2
    CS20Q4.specs[870] = 1.3
    CS20Q4.specs[1000] = 1.4
    CS20Q4.specs[1218] = 1.8
    MainActivity.attenuatorMap[CS20Q4.name()] = CS20Q4

    // CommScope FFT 20Q 8 port tap
    val CS20Q8 = Attenuator(name = "FFT 20Q 8 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS20Q8.specs[5] = 1.1
    CS20Q8.specs[10] = 0.9
    CS20Q8.specs[50] = 0.9
    CS20Q8.specs[100] = 1.1
    CS20Q8.specs[450] = 1.3
    CS20Q8.specs[550] = 1.5
    CS20Q8.specs[750] = 1.7
    CS20Q8.specs[870] = 1.8
    CS20Q8.specs[1000] = 1.8
    CS20Q8.specs[1218] = 2.2
    MainActivity.attenuatorMap[CS20Q8.name()] = CS20Q8

    // CommScope FFT 17Q 2 port tap
    val CS17Q2 = Attenuator(name = "FFT 17Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS17Q2.specs[5] = 0.7
    CS17Q2.specs[10] = 0.6
    CS17Q2.specs[50] = 0.5
    CS17Q2.specs[100] = 0.8
    CS17Q2.specs[450] = 1.0
    CS17Q2.specs[550] = 1.1
    CS17Q2.specs[750] = 1.2
    CS17Q2.specs[870] = 1.3
    CS17Q2.specs[1000] = 1.4
    CS17Q2.specs[1218] = 1.7
    MainActivity.attenuatorMap[CS17Q2.name()] = CS17Q2

    // CommScope FFT 17Q 4 port tap
    val CS17Q4 = Attenuator(name = "FFT 17Q 4 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS17Q4.specs[5] = 0.9
    CS17Q4.specs[10] = 0.7
    CS17Q4.specs[50] = 0.7
    CS17Q4.specs[100] = 0.9
    CS17Q4.specs[450] = 1.2
    CS17Q4.specs[550] = 1.3
    CS17Q4.specs[750] = 1.5
    CS17Q4.specs[870] = 1.6
    CS17Q4.specs[1000] = 1.8
    CS17Q4.specs[1218] = 2.2
    MainActivity.attenuatorMap[CS17Q4.name()] = CS17Q4

    // CommScope FFT 17Q 8 port tap
    val CS17Q8 = Attenuator(name = "FFT 17Q 8 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS17Q8.specs[5] = 1.6
    CS17Q8.specs[10] = 1.3
    CS17Q8.specs[50] = 1.2
    CS17Q8.specs[100] = 1.4
    CS17Q8.specs[450] = 1.7
    CS17Q8.specs[550] = 2.0
    CS17Q8.specs[750] = 2.2
    CS17Q8.specs[870] = 2.4
    CS17Q8.specs[1000] = 2.4
    CS17Q8.specs[1218] = 3.0
    MainActivity.attenuatorMap[CS17Q8.name()] = CS17Q8

    // CommScope FFT 14Q 2 port tap
    val CS14Q2 = Attenuator(name = "FFT 14Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS14Q2.specs[5] = 0.9
    CS14Q2.specs[10] = 0.7
    CS14Q2.specs[50] = 0.6
    CS14Q2.specs[100] = 0.8
    CS14Q2.specs[450] = 1.1
    CS14Q2.specs[550] = 1.2
    CS14Q2.specs[750] = 1.3
    CS14Q2.specs[870] = 1.4
    CS14Q2.specs[1000] = 1.6
    CS14Q2.specs[1218] = 1.9
    MainActivity.attenuatorMap[CS14Q2.name()] = CS14Q2

    // CommScope FFT 14Q 4 port tap
    val CS14Q4 = Attenuator(name = "FFT 14Q 4 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS14Q4.specs[5] = 1.7
    CS14Q4.specs[10] = 1.3
    CS14Q4.specs[50] = 1.2
    CS14Q4.specs[100] = 1.4
    CS14Q4.specs[450] = 1.8
    CS14Q4.specs[550] = 2.0
    CS14Q4.specs[750] = 2.2
    CS14Q4.specs[870] = 2.4
    CS14Q4.specs[1000] = 2.7
    CS14Q4.specs[1218] = 3.1
    MainActivity.attenuatorMap[CS14Q4.name()] = CS14Q4

    // CommScope FFT 14Q 8 port tap
    val CS14Q8 = Attenuator(name = "FFT 14Q 8 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS14Q8.specs[5] = 3.7
    CS14Q8.specs[10] = 3.3
    CS14Q8.specs[50] = 3.2
    CS14Q8.specs[100] = 3.4
    CS14Q8.specs[450] = 3.9
    CS14Q8.specs[550] = 4.0
    CS14Q8.specs[750] = 4.1
    CS14Q8.specs[870] = 4.1
    CS14Q8.specs[1000] = 4.1
    CS14Q8.specs[1218] = 4.5
    MainActivity.attenuatorMap[CS14Q8.name()] = CS14Q8

    // CommScope FFT 15.5Q 4 port tap
    val CS155Q4 = Attenuator(name = "FFT 15.5Q 4 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS155Q4.specs[5] = 1.3
    CS155Q4.specs[10] = 1.0
    CS155Q4.specs[50] = 0.9
    CS155Q4.specs[100] = 1.2
    CS155Q4.specs[450] = 1.5
    CS155Q4.specs[550] = 1.7
    CS155Q4.specs[750] = 1.9
    CS155Q4.specs[870] = 2.1
    CS155Q4.specs[1000] = 2.3
    CS155Q4.specs[1218] = 2.7
    MainActivity.attenuatorMap[CS155Q4.name()] = CS155Q4

    // CommScope FFT 12Q 2 port tap
    val CS12Q2 = Attenuator(name = "FFT 12Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS12Q2.specs[5] = 1.4
    CS12Q2.specs[10] = 1.1
    CS12Q2.specs[50] = 1.0
    CS12Q2.specs[100] = 1.3
    CS12Q2.specs[450] = 1.6
    CS12Q2.specs[550] = 1.7
    CS12Q2.specs[750] = 1.9
    CS12Q2.specs[870] = 2.0
    CS12Q2.specs[1000] = 2.3
    CS12Q2.specs[1218] = 2.6
    MainActivity.attenuatorMap[CS12Q2.name()] = CS12Q2

    // CommScope FFT 10Q 2 port tap
    val CS10Q2 = Attenuator(name = "FFT 10Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS10Q2.specs[5] = 1.7
    CS10Q2.specs[10] = 1.4
    CS10Q2.specs[50] = 1.3
    CS10Q2.specs[100] = 1.5
    CS10Q2.specs[450] = 1.8
    CS10Q2.specs[550] = 2.0
    CS10Q2.specs[750] = 2.2
    CS10Q2.specs[870] = 2.3
    CS10Q2.specs[1000] = 2.6
    CS10Q2.specs[1218] = 2.9
    MainActivity.attenuatorMap[CS10Q2.name()] = CS10Q2

    // CommScope FFT 10Q 4 port tap
    val CS10Q4 = Attenuator(name = "FFT 10Q 4 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS10Q4.specs[5] = 3.8
    CS10Q4.specs[10] = 3.3
    CS10Q4.specs[50] = 3.2
    CS10Q4.specs[100] = 3.5
    CS10Q4.specs[450] = 3.9
    CS10Q4.specs[550] = 4.0
    CS10Q4.specs[750] = 4.2
    CS10Q4.specs[870] = 4.1
    CS10Q4.specs[1000] = 4.1
    CS10Q4.specs[1218] = 4.6
    MainActivity.attenuatorMap[CS10Q4.name()] = CS10Q4

    // CommScope FFT 7Q 2 port tap
    val CS7Q2 = Attenuator(name = "FFT 7Q 2 port tap", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CS7Q2.specs[5] = 3.2
    CS7Q2.specs[10] = 2.8
    CS7Q2.specs[50] = 2.7
    CS7Q2.specs[100] = 3.0
    CS7Q2.specs[450] = 3.3
    CS7Q2.specs[550] = 3.3
    CS7Q2.specs[750] = 3.4
    CS7Q2.specs[870] = 3.4
    CS7Q2.specs[1000] = 3.5
    CS7Q2.specs[1218] = 4.0
    MainActivity.attenuatorMap[CS7Q2.name()] = CS7Q2

    // CommScope SSP-PIQ power inserter
    val CSSSPPIQ = Attenuator(name = "SSP-PIQ", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSPPIQ.specs[5] = 0.3
    CSSSPPIQ.specs[50] = 0.3
    CSSSPPIQ.specs[100] = 0.3
    CSSSPPIQ.specs[450] = 0.4
    CSSSPPIQ.specs[550] = 0.4
    CSSSPPIQ.specs[750] = 0.4
    CSSSPPIQ.specs[870] = 0.5
    CSSSPPIQ.specs[1000] = 0.5
    CSSSPPIQ.specs[1218] = 0.9
    MainActivity.attenuatorMap[CSSSPPIQ.name()] = CSSSPPIQ

    // CommScope SSP 3Q two way splitter
    val CSSSP3Q = Attenuator(name = "SSP-3Q", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP3Q.specs[5] = 3.8
    CSSSP3Q.specs[50] = 3.8
    CSSSP3Q.specs[100] = 3.8
    CSSSP3Q.specs[450] = 3.8
    CSSSP3Q.specs[550] = 3.9
    CSSSP3Q.specs[750] = 4.1
    CSSSP3Q.specs[870] = 4.3
    CSSSP3Q.specs[1000] = 4.5
    CSSSP3Q.specs[1218] = 5.0
    MainActivity.attenuatorMap[CSSSP3Q.name()] = CSSSP3Q

    // CommScope SSP 7Q thru leg
    val CSSSP7QL = Attenuator(name = "SSP-7Q Thru Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP7QL.specs[5] = 1.9
    CSSSP7QL.specs[50] = 1.9
    CSSSP7QL.specs[100] = 1.9
    CSSSP7QL.specs[450] = 2.1
    CSSSP7QL.specs[550] = 2.1
    CSSSP7QL.specs[650] = 2.4
    CSSSP7QL.specs[750] = 2.6
    CSSSP7QL.specs[870] = 2.8
    CSSSP7QL.specs[1000] = 2.8
    CSSSP7QL.specs[1218] = 3.4
    MainActivity.attenuatorMap[CSSSP7QL.name()] = CSSSP7QL

    // CommScope SSP 7Q tap leg
    val CSSSP7QH = Attenuator(name = "SSP-7Q Tap Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP7QH.specs[5] = 7.7
    CSSSP7QH.specs[50] = 7.7
    CSSSP7QH.specs[100] = 7.7
    CSSSP7QH.specs[450] = 7.6
    CSSSP7QH.specs[550] = 7.5
    CSSSP7QH.specs[650] = 7.5
    CSSSP7QH.specs[750] = 7.6
    CSSSP7QH.specs[870] = 7.9
    CSSSP7QH.specs[1000] = 7.9
    CSSSP7QH.specs[1218] = 8.5
    MainActivity.attenuatorMap[CSSSP7QH.name()] = CSSSP7QH

    // CommScope SSP 9Q thru leg
    val CSSSP9QL = Attenuator(name = "SSP-9Q Thru Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP9QL.specs[5] = 1.4
    CSSSP9QL.specs[50] = 1.4
    CSSSP9QL.specs[100] = 1.4
    CSSSP9QL.specs[450] = 1.6
    CSSSP9QL.specs[550] = 1.6
    CSSSP9QL.specs[650] = 1.8
    CSSSP9QL.specs[750] = 2.1
    CSSSP9QL.specs[870] = 2.3
    CSSSP9QL.specs[1000] = 2.3
    CSSSP9QL.specs[1218] = 2.9
    MainActivity.attenuatorMap[CSSSP9QL.name()] = CSSSP9QL

    // CommScope SSP 9Q tap leg
    val CSSSP9QH = Attenuator(name = "SSP-9Q Tap Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP9QH.specs[5] = 9.3
    CSSSP9QH.specs[50] = 9.3
    CSSSP9QH.specs[100] = 9.3
    CSSSP9QH.specs[450] = 9.2
    CSSSP9QH.specs[550] = 9.1
    CSSSP9QH.specs[650] = 9.1
    CSSSP9QH.specs[750] = 9.1
    CSSSP9QH.specs[870] = 9.4
    CSSSP9QH.specs[1000] = 9.4
    CSSSP9QH.specs[1218] = 10.0
    MainActivity.attenuatorMap[CSSSP9QH.name()] = CSSSP9QH

    // CommScope SSP 12Q thru leg
    val CSSSP12QL = Attenuator(name = "SSP-12Q Thru Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP12QL.specs[5] = 1.1
    CSSSP12QL.specs[50] = 1.1
    CSSSP12QL.specs[100] = 1.1
    CSSSP12QL.specs[450] = 1.3
    CSSSP12QL.specs[550] = 1.4
    CSSSP12QL.specs[650] = 1.5
    CSSSP12QL.specs[750] = 1.6
    CSSSP12QL.specs[870] = 1.8
    CSSSP12QL.specs[1000] = 2.0
    CSSSP12QL.specs[1218] = 2.2
    MainActivity.attenuatorMap[CSSSP12QL.name()] = CSSSP12QL

    // CommScope SSP 12Q tap leg
    val CSSSP12QH = Attenuator(name = "SSP-12Q Tap Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP12QH.specs[5] = 11.7
    CSSSP12QH.specs[50] = 11.7
    CSSSP12QH.specs[100] = 11.7
    CSSSP12QH.specs[450] = 11.4
    CSSSP12QH.specs[550] = 11.4
    CSSSP12QH.specs[650] = 11.5
    CSSSP12QH.specs[750] = 11.6
    CSSSP12QH.specs[870] = 11.9
    CSSSP12QH.specs[1000] = 12.2
    CSSSP12QH.specs[1218] = 12.7
    MainActivity.attenuatorMap[CSSSP12QH.name()] = CSSSP12QH

    // CommScope SSP 16Q thru leg
    val CSSSP16QL = Attenuator(name = "SSP-16Q Thru Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP16QL.specs[5] = 0.9
    CSSSP16QL.specs[50] = 0.9
    CSSSP16QL.specs[100] = 0.9
    CSSSP16QL.specs[450] = 1.0
    CSSSP16QL.specs[550] = 1.0
    CSSSP16QL.specs[650] = 1.1
    CSSSP16QL.specs[750] = 1.3
    CSSSP16QL.specs[870] = 1.5
    CSSSP16QL.specs[1000] = 1.7
    CSSSP16QL.specs[1218] = 1.9
    MainActivity.attenuatorMap[CSSSP16QL.name()] = CSSSP16QL

    // CommScope SSP 16Q tap leg
    val CSSSP16QH = Attenuator(name = "SSP-16Q Tap Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP16QH.specs[5] = 15.7
    CSSSP16QH.specs[50] = 15.7
    CSSSP16QH.specs[100] = 15.7
    CSSSP16QH.specs[450] = 15.4
    CSSSP16QH.specs[550] = 15.3
    CSSSP16QH.specs[650] = 15.4
    CSSSP16QH.specs[750] = 15.5
    CSSSP16QH.specs[870] = 15.9
    CSSSP16QH.specs[1000] = 16.2
    CSSSP16QH.specs[1218] = 16.6
    MainActivity.attenuatorMap[CSSSP16QH.name()] = CSSSP16QH

    // CommScope SSP 636Q high leg
    val CSSSP636H = Attenuator(name = "SSP-636Q High Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP636H.specs[5] = 7.2
    CSSSP636H.specs[50] = 7.2
    CSSSP636H.specs[100] = 7.2
    CSSSP636H.specs[450] = 7.5
    CSSSP636H.specs[550] = 7.5
    CSSSP636H.specs[650] = 7.6
    CSSSP636H.specs[750] = 7.7
    CSSSP636H.specs[870] = 7.9
    CSSSP636H.specs[1000] = 8.3
    CSSSP636H.specs[1218] = 8.8
    MainActivity.attenuatorMap[CSSSP636H.name()] = CSSSP636H

    // CommScope SSP 636Q low leg
    val CSSSP636QL = Attenuator(name = "SSP-636Q Low Leg", tags = listOf(passive, plant),
        iscoax = false, ispassive = true, isdrop = false, isplant = true)
    CSSSP636QL.specs[5] = 3.9
    CSSSP636QL.specs[50] = 3.9
    CSSSP636QL.specs[100] = 3.9
    CSSSP636QL.specs[450] = 4.0
    CSSSP636QL.specs[550] = 4.1
    CSSSP636QL.specs[650] = 4.2
    CSSSP636QL.specs[750] = 4.2
    CSSSP636QL.specs[870] = 4.4
    CSSSP636QL.specs[1000] = 4.6
    CSSSP636QL.specs[1218] = 4.9
    MainActivity.attenuatorMap[CSSSP636QL.name()] = CSSSP636QL
}