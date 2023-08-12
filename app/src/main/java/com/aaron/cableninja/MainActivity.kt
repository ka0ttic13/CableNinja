package com.aaron.cableninja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aaron.cableninja.model.RFData
import com.aaron.cableninja.navigation.SetupNavGraph
import com.aaron.cableninja.screen.AttenuatorCard
import com.aaron.cableninja.ui.theme.CableNinjaTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    companion object {
        var attenuatorDataList = mutableListOf<RFData>()
        var attenuatorCardList = mutableListOf<AttenuatorCard>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CableNinjaTheme {
                // load RF attenuation data
                _loadRFdata()

                // Start navigation controller and show MainScreen
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }

    // TODO load data from XML?
    private fun _loadRFdata() {
        val RG6 = RFData("RG6", "Drop/Coax", true)
        RG6.dataMap[5] = 0.58
        RG6.dataMap[55] = 1.6
        attenuatorDataList.add(RG6)

        val RG11 = RFData("RG11", "Drop/Coax", true)
        RG11.dataMap[5] = .38
        RG11.dataMap[55] = 0.96
        attenuatorDataList.add(RG11)

        val TwoWay = RFData("2 Way", "Passive/Drop", false)
        TwoWay.dataMap[5] = 3.5
        attenuatorDataList.add(TwoWay)
    }
}

