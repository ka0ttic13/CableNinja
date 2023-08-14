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
        val RG6 = ManufacturerSpecs("RG6",
            "Drop/Coax", true)
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

        val RG11 = ManufacturerSpecs("RG11",
            "Drop/Coax", true)
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

        val TwoWay = ManufacturerSpecs("2 Way / 3 Way Low Leg",
            "Passive/Drop", false)
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

        val ThreeWayBalanced = ManufacturerSpecs("3 Way Balanced",
            "Passive/Drop", iscoax = false)
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

        val FourWay = ManufacturerSpecs("4 Way / 3 Way High Leg",
            "Passive/Drop", iscoax = false)
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

        val EightWay = ManufacturerSpecs("8 Way",
            "Passive/Drop", iscoax = false)
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

        // TODO
        //val CS625 = AttenuatorData("P3 600", "Coax/Plant", iscoax = true)

    }
}

