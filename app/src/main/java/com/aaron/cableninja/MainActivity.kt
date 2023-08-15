package com.aaron.cableninja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aaron.cableninja.domain.ManufacturerSpecs
import com.aaron.cableninja.presentation.ui.SetupNavGraph
import com.aaron.cableninja.domain.AttenuatorCard
import com.aaron.cableninja.presentation.ui.theme.CableNinjaTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    companion object {
        // master list of RF data that can be added
        var manufacturerSpecsMap = mutableMapOf<String, ManufacturerSpecs>()
        // master list of RF data that has been added
        var attenuatorCardList = mutableListOf<AttenuatorCard>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CableNinjaTheme {
                // load manufacturer specs
                _loadRFdata()

                // Start navigation controller and show MainScreen
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }

    private fun _loadRFdata() {
        val RG6 = ManufacturerSpecs("RG6", "Coax", true)
        RG6.dataMap[5] = 0.58
        RG6.dataMap[55] = 1.6
        RG6.dataMap[211] = 3.05
        RG6.dataMap[250] = 3.3
        RG6.dataMap[270] = 3.37
        RG6.dataMap[300] = 3.55
        RG6.dataMap[330] = 3.74
        RG6.dataMap[350] = 3.85
        RG6.dataMap[400] = 4.15
        RG6.dataMap[450] = 4.4
        RG6.dataMap[500] = 4.66
        RG6.dataMap[550] = 4.9
        RG6.dataMap[600] = 5.1
        RG6.dataMap[750] = 5.65
        RG6.dataMap[870] = 6.11
        RG6.dataMap[1000] = 6.55
        RG6.dataMap[1100] = 6.87
        RG6.dataMap[1200] = 7.18
        RG6.dataMap[1218] = 7.21
        manufacturerSpecsMap[RG6.id()] = RG6

        val RG11 = ManufacturerSpecs("RG11", "Coax", true)
        RG11.dataMap[5] = .38
        RG11.dataMap[55] = 0.96
        RG11.dataMap[211] = 1.9
        RG11.dataMap[250] = 2.05
        RG11.dataMap[270] = 2.13
        RG11.dataMap[300] = 2.25
        RG11.dataMap[330] = 2.35
        RG11.dataMap[350] = 2.42
        RG11.dataMap[400] = 2.6
        RG11.dataMap[450] = 2.75
        RG11.dataMap[500] = 2.9
        RG11.dataMap[550] = 3.04
        RG11.dataMap[600] = 3.18
        RG11.dataMap[750] = 3.65
        RG11.dataMap[870] = 4.06
        RG11.dataMap[1000] = 4.35
        RG11.dataMap[1100] = 4.54
        RG11.dataMap[1200] = 4.71
        RG11.dataMap[1218] = 4.92
        manufacturerSpecsMap[RG11.id()] = RG11

        val TwoWay = ManufacturerSpecs("2 Way / 3 Way Low Leg", "Passive", false)
        TwoWay.dataMap[5] = 3.5
        TwoWay.dataMap[55] = 3.5
        TwoWay.dataMap[211] = 3.7
        TwoWay.dataMap[250] = 3.7
        TwoWay.dataMap[400] = 3.7
        TwoWay.dataMap[550] = 3.8
        TwoWay.dataMap[750] = 3.8
        TwoWay.dataMap[870] = 4.2
        TwoWay.dataMap[1000] = 4.2
        TwoWay.dataMap[1100] = 4.6
        TwoWay.dataMap[1200] = 4.6
        manufacturerSpecsMap[TwoWay.id()] = TwoWay

        val ThreeWayBalanced = ManufacturerSpecs("3 Way Balanced", "Passive", false)
        ThreeWayBalanced.dataMap[5] = 5.8
        ThreeWayBalanced.dataMap[55] = 5.8
        ThreeWayBalanced.dataMap[211] = 5.9
        ThreeWayBalanced.dataMap[250] = 6.0
        ThreeWayBalanced.dataMap[400] = 6.0
        ThreeWayBalanced.dataMap[550] = 6.1
        ThreeWayBalanced.dataMap[750] = 6.2
        ThreeWayBalanced.dataMap[870] = 6.8
        ThreeWayBalanced.dataMap[1000] = 6.8
        ThreeWayBalanced.dataMap[1100] = 7.5
        ThreeWayBalanced.dataMap[1200] = 7.5
        manufacturerSpecsMap[ThreeWayBalanced.id()] = ThreeWayBalanced

        val FourWay = ManufacturerSpecs("4 Way / 3 Way High Leg", "Passive", false)
        FourWay.dataMap[5] = 7.0
        FourWay.dataMap[55] = 7.2
        FourWay.dataMap[211] = 7.2
        FourWay.dataMap[250] = 7.4
        FourWay.dataMap[550] = 7.4
        FourWay.dataMap[750] = 7.6
        FourWay.dataMap[870] = 8.5
        FourWay.dataMap[1000] = 8.5
        FourWay.dataMap[1100] = 8.9
        FourWay.dataMap[1200] = 8.9
        manufacturerSpecsMap[FourWay.id()] = FourWay

        val EightWay = ManufacturerSpecs("8 Way", "Passive", false)
        EightWay.dataMap[5] = 11.2
        EightWay.dataMap[55] = 11.0
        EightWay.dataMap[211] = 11.2
        EightWay.dataMap[250] = 11.5
        EightWay.dataMap[550] = 11.5
        EightWay.dataMap[750] = 12.0
        EightWay.dataMap[870] = 12.5
        EightWay.dataMap[1000] = 12.5
        EightWay.dataMap[1100] = 12.7
        EightWay.dataMap[1200] = 12.9
        manufacturerSpecsMap[EightWay.id()] = EightWay

        val P3500 = ManufacturerSpecs("0.500 P3", "Coax", true)
        P3500.dataMap[5] = 0.16
        P3500.dataMap[55] = 0.54
        P3500.dataMap[85] = 0.68
        P3500.dataMap[204] = 1.07
        P3500.dataMap[211] = 1.09
        P3500.dataMap[250] = 1.2
        P3500.dataMap[300] = 1.31
        P3500.dataMap[350] = 1.43
        P3500.dataMap[400] = 1.53
        P3500.dataMap[450] = 1.63
        P3500.dataMap[500] = 1.73
        P3500.dataMap[550] = 1.82
        P3500.dataMap[600] = 1.92
        P3500.dataMap[750] = 2.16
        P3500.dataMap[865] = 2.34
        P3500.dataMap[1002] = 2.54
        P3500.dataMap[1218] = 2.83
        manufacturerSpecsMap[P3500.id()] = P3500

        val P3625 = ManufacturerSpecs("0.625 P3", "Coax", true)
        P3625.dataMap[5] = 0.13
        P3625.dataMap[55] = 0.45
        P3625.dataMap[85] = 0.56
        P3625.dataMap[204] = 0.89
        P3625.dataMap[211] = 0.92
        P3625.dataMap[250] = 1.0
        P3625.dataMap[300] = 1.08
        P3625.dataMap[350] = 1.18
        P3625.dataMap[400] = 1.27
        P3625.dataMap[450] = 1.35
        P3625.dataMap[500] = 1.43
        P3625.dataMap[550] = 1.5
        P3625.dataMap[600] = 1.58
        P3625.dataMap[750] = 1.78
        P3625.dataMap[865] = 1.93
        P3625.dataMap[1002] = 2.11
        P3625.dataMap[1218] = 2.32
        manufacturerSpecsMap[P3625.id()] = P3625

        val P3750 = ManufacturerSpecs("0.750 P3", "Coax", true)
        P3750.dataMap[5] = 0.11
        P3750.dataMap[55] = 0.37
        P3750.dataMap[85] = 0.46
        P3750.dataMap[204] = 0.72
        P3750.dataMap[211] = 0.74
        P3750.dataMap[250] = 0.81
        P3750.dataMap[300] = 0.89
        P3750.dataMap[350] = 0.97
        P3750.dataMap[400] = 1.05
        P3750.dataMap[450] = 1.12
        P3750.dataMap[500] = 1.18
        P3750.dataMap[550] = 1.24
        P3750.dataMap[600] = 1.31
        P3750.dataMap[750] = 1.48
        P3750.dataMap[865] = 1.61
        P3750.dataMap[1002] = 1.74
        P3750.dataMap[1218] = 1.95
        manufacturerSpecsMap[P3750.id()] = P3750

        val P3875 = ManufacturerSpecs("0.875 P3", "Coax", true)
        P3875.dataMap[5] = 0.09
        P3875.dataMap[55] = 0.33
        P3875.dataMap[85] = 0.4
        P3875.dataMap[204] = 0.63
        P3875.dataMap[211] = 0.66
        P3875.dataMap[250] = 0.72
        P3875.dataMap[300] = 0.78
        P3875.dataMap[350] = 0.84
        P3875.dataMap[400] = 0.91
        P3875.dataMap[450] = 0.97
        P3875.dataMap[500] = 1.03
        P3875.dataMap[550] = 1.08
        P3875.dataMap[600] = 1.14
        P3875.dataMap[750] = 1.29
        P3875.dataMap[865] = 1.41
        P3875.dataMap[1002] = 1.53
        P3875.dataMap[1218] = 1.7
        manufacturerSpecsMap[P3875.id()] = P3875

        val QR540 = ManufacturerSpecs("0.540 QR", "Coax", true)
        QR540.dataMap[5] = 0.14
        QR540.dataMap[55] = 0.47
        QR540.dataMap[85] = 0.59
        QR540.dataMap[204] = 0.93
        QR540.dataMap[211] = 0.95
        QR540.dataMap[250] = 1.03
        QR540.dataMap[300] = 1.13
        QR540.dataMap[350] = 1.23
        QR540.dataMap[400] = 1.32
        QR540.dataMap[450] = 1.4
        QR540.dataMap[500] = 1.49
        QR540.dataMap[550] = 1.56
        QR540.dataMap[600] = 1.64
        QR540.dataMap[750] = 1.85
        QR540.dataMap[865] = 2.0
        QR540.dataMap[1002] = 2.17
        QR540.dataMap[1218] = 2.41
        manufacturerSpecsMap[QR540.id()] = QR540

        val QR715 = ManufacturerSpecs("0.715 QR", "Coax", true)
        QR715.dataMap[5] = 0.11
        QR715.dataMap[55] = 0.37
        QR715.dataMap[85] = 0.46
        QR715.dataMap[204] = 0.73
        QR715.dataMap[211] = 0.74
        QR715.dataMap[250] = 0.81
        QR715.dataMap[300] = 0.89
        QR715.dataMap[350] = 0.97
        QR715.dataMap[400] = 1.05
        QR715.dataMap[450] = 1.12
        QR715.dataMap[500] = 1.19
        QR715.dataMap[550] = 1.25
        QR715.dataMap[600] = 1.31
        QR715.dataMap[750] = 1.49
        QR715.dataMap[865] = 1.62
        QR715.dataMap[1002] = 1.75
        QR715.dataMap[1218] = 1.96
        manufacturerSpecsMap[QR715.id()] = QR715

        val QR860 = ManufacturerSpecs("0.860 QR", "Coax", true)
        QR860.dataMap[5] = 0.09
        QR860.dataMap[55] = 0.32
        QR860.dataMap[85] = 0.4
        QR860.dataMap[204] = 0.63
        QR860.dataMap[211] = 0.64
        QR860.dataMap[250] = 0.7
        QR860.dataMap[300] = 0.76
        QR860.dataMap[350] = 0.83
        QR860.dataMap[400] = 0.88
        QR860.dataMap[450] = 0.95
        QR860.dataMap[500] = 1.0
        QR860.dataMap[550] = 1.06
        QR860.dataMap[600] = 1.1
        QR860.dataMap[750] = 1.24
        QR860.dataMap[865] = 1.33
        QR860.dataMap[1000] = 1.44
        QR860.dataMap[1002] = 1.45
        QR860.dataMap[1218] = 1.61
        manufacturerSpecsMap[QR860.id()] = QR860


    }
}

