package com.aaron.cableninja.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.domain.AttenuatorCard
import com.aaron.cableninja.domain.AttenuatorTag
import com.aaron.cableninja.ui.navigation.SetupNavGraph
import com.aaron.cableninja.ui.theme.CableNinjaTheme
import com.aaron.cableninja.ui.theme.coaxColor
import com.aaron.cableninja.ui.theme.dropColor
import com.aaron.cableninja.ui.theme.passiveColor
import com.aaron.cableninja.ui.theme.plantColor

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    companion object {
        // map of attenuator tags to colors
        var attenuatorTags = mutableMapOf<AttenuatorType, Color>()
        // master map of ID strings to manufacturer data
        var attenuatorMap = mutableMapOf<String, Attenuator>()
        // master list of RF data that has been added
        var attenuatorCardList = mutableListOf<AttenuatorCard>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CableNinjaTheme {
                // Lock screen orientation to portrait
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                // load manufacturer specs
                _loadRFdata()

                // load tags
                _loadAttenuatorColors()

                // Start navigation controller and show MainScreen
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }

    private fun _loadAttenuatorColors() {
        attenuatorTags[AttenuatorType.COAX] = coaxColor
        attenuatorTags[AttenuatorType.PASSIVE] = passiveColor
        attenuatorTags[AttenuatorType.DROP] = dropColor
        attenuatorTags[AttenuatorType.PLANT] = plantColor
    }

    private fun _loadRFdata() {
        val coax    = AttenuatorTag(AttenuatorType.COAX)
        val passive = AttenuatorTag(AttenuatorType.PASSIVE)
        val drop    = AttenuatorTag(AttenuatorType.DROP)
        val plant   = AttenuatorTag(AttenuatorType.PLANT)

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
        attenuatorMap[RG59.name()] = RG59

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
        attenuatorMap[RG6.name()] = RG6

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
        attenuatorMap[RG11.name()] = RG11

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
        attenuatorMap[TwoWay.name()] = TwoWay

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
        attenuatorMap[ThreeWayBalanced.name()] = ThreeWayBalanced

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
        attenuatorMap[FourWay.name()] = FourWay

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
        attenuatorMap[EightWay.name()] = EightWay

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
        attenuatorMap[P3500.name()] = P3500

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
        attenuatorMap[P3625.name()] = P3625

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
        attenuatorMap[P3750.name()] = P3750

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
        attenuatorMap[P3875.name()] = P3875

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
        attenuatorMap[QR540.name()] = QR540

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
        attenuatorMap[QR715.name()] = QR715

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
        attenuatorMap[QR860.name()] = QR860

    }
}

