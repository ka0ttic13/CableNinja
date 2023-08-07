package com.aaron.cableninja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aaron.cableninja.ui.theme.CableNinjaTheme

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var navController: NavHostController
        var attenuatorList = mutableListOf<Attenuator>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CableNinjaTheme {
                // load attenuation data
                loadRFdata()

                // Start navigation controller and show MainScreen
                navController = rememberNavController()
                SetupNavGraph(navController = navController)

            }
        }
    }

    private fun loadRFdata() {
        val RG6 = Attenuator("RG6", "Drop/Coax")
        RG6.RFdata[5] = 0.58
        RG6.RFdata[55] = 1.6
        attenuatorList.add(RG6)

        val RG11 = Attenuator("RG11", "Drop/Coax")
        RG11.RFdata[5] = .38
        RG11.RFdata[55] = 0.96
        attenuatorList.add(RG11)

        val TwoWay = Attenuator("2 Way", "Passive/Drop")
        TwoWay.RFdata[5] = 3.5
        attenuatorList.add(TwoWay)
    }
}

