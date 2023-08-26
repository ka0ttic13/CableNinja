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
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.data.AttenuatorTag
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
                loadRFdata()

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
}

