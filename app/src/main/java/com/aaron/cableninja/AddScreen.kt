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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aaron.cableninja.MainActivity.Companion.attenuatorList
import com.aaron.cableninja.MainActivity.Companion.navController
import com.aaron.cableninja.ui.theme.CableNinjaTheme


@Composable
fun AddScreen() {

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
        // iterate over attenuatorList and create an AttenuatorCard for
        // each Attenuator in the list
        attenuatorList.forEach() {
            AttenuatorCard(it.id(), it.desc())
        }
    }
}

@Composable
private fun AttenuatorCard(id: String, desc: String) {
    val addClick = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clickable { addClick.value = true }
            .padding(36.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = id,
            fontWeight = FontWeight.Bold
        )
        Text(text = desc)
    }

    if (addClick.value) {
        AddAttenuator(id)
        navController.popBackStack()
    }
}

@Composable
private fun AddAttenuator(id: String) {
    Column() {
        Text(text = "Adding $id...")
    }
}

@Preview(showBackground = true)
@Composable
private fun AttenuatorCardPreview() {
    CableNinjaTheme {
        AttenuatorCard("RG6", "Coax")
    }
}