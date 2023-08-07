package com.aaron.cableninja

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aaron.cableninja.ui.theme.CableNinjaTheme

@Composable
fun AddScreen(navController: NavController) {

    // Add Label with Close Icon "X"
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        // Add Label
        Text(text = "Add attenuator")

        // Close Icon - exits back to MainScreen
        Icon(
            painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "Close",
            modifier = Modifier
            .clickable {
                // on click, go back to MainSCreen()
                navController.popBackStack()
            }
        )
    }

    // Show attenuator types that can be added
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 60.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        // TODO make new AttenuatorCard() that takes 1 string, for example
        // TODO AttenuatorCard("Drop/Coax/RG6")

        AttenuatorCard(name = "RG6", "Drop/Coax")
        AttenuatorCard(name = "RG59", "Drop/Coax")
        AttenuatorCard(name = "RG11", "Drop/Coax")
        AttenuatorCard(name = "2 Way", "Drop/Passive")
        AttenuatorCard(name = "3 Way low", "Drop/Passive")
        AttenuatorCard(name = "3 Way high", "Drop/Passive")
        AttenuatorCard(name = "500P3", "Hardline/Coax")
        AttenuatorCard(name = "625P3", "Hardline/Coax")
    }
}

@Composable
private fun AttenuatorCard(name: String, desc: String = "") {
    Column(
        modifier = Modifier
            .clickable {
                // TODO add type that is clicked
            }
            .padding(36.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold
        )
        Text(text = desc)
    }
}

@Preview(showBackground = true)
@Composable
private fun AttenuatorCardPreview() {
    CableNinjaTheme {
        AttenuatorCard(name = "RG6")
    }
}