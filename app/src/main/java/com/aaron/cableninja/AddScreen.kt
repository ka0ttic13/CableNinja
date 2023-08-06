package com.aaron.cableninja

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aaron.cableninja.ui.theme.CableNinjaTheme

@Composable
fun AddScreen() {
    Text(text = "Add attenuator", modifier = Modifier
        .padding(20.dp)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxSize()
    ) {
        AttenuatorCard(name = "RG6")
        AttenuatorCard(name = "RG59")
        AttenuatorCard(name = "RG11")
        AttenuatorCard(name = "2 Way")
        AttenuatorCard(name = "3 Way low")
        AttenuatorCard(name = "3 Way high")
    }
}

@Composable
private fun AttenuatorCard(name: String) {
    Column(
        modifier = Modifier
            .clickable {  }
            .padding(36.dp)
            .fillMaxWidth()
    ) {
        Text(name)
        // TODO make title bold
    }
}

@Preview(showBackground = true)
@Composable
private fun AttenuatorCardPreview() {
    CableNinjaTheme {
        AttenuatorCard(name = "RG6")
    }
}