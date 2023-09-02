package com.aaron.cableninja.ui.addscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aaron.cableninja.R
import com.aaron.cableninja.ui.viewmodels.SharedViewModel
import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.data.getCableLoss
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.ui.dialogs.LengthDialog
import com.aaron.cableninja.ui.navigation.Screen
import com.aaron.cableninja.ui.theme.coaxColor
import com.aaron.cableninja.ui.theme.dropColor
import com.aaron.cableninja.ui.theme.passiveColor
import com.aaron.cableninja.ui.theme.plantColor


/*********************************************************************************
 * AddScreen()
 *      Show attenuators that can be added.
 *********************************************************************************/
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AddScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {
    var showLengthDialog by remember { mutableStateOf(false) }

    var search by remember { mutableStateOf("") }
    var doSearch by remember { mutableStateOf(false) }
    var dontSearch by remember { mutableStateOf(false) }
    var doFilter by remember { mutableStateOf(false) }
    var dontFilter by remember { mutableStateOf(false) }

    var coaxFilter by remember { mutableStateOf(false) }
    var passiveFilter by remember { mutableStateOf(false) }
    var dropFilter by remember { mutableStateOf(false) }
    var plantFilter by remember { mutableStateOf(false) }

    val kbController = LocalSoftwareKeyboardController.current

    var showList = mutableListOf<Attenuator>()

    Column {
        // Add Header with Close Icon "X" on right side
        //      cancels Add operation
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            // Add Label
            Text(
                text = "Add attenuator",
                fontWeight = FontWeight.Bold
            )

            // Close Icon - exits back to MainScreen
            Icon(
                painterResource(id = R.drawable.baseline_close_24),
                contentDescription = "Close",
                modifier = Modifier.clickable {
                    // on click, go back to MainScreen()
                    navController.popBackStack()
                }
            )
        }

        Divider(modifier = Modifier.padding(bottom = 6.dp))

        // search box
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it.trim()
                    if (search.isNotEmpty())
                        doSearch = true
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Perform search",
                    )
                },
                trailingIcon = {
                    if (search.isNotEmpty()) {
                        Icon(
                            painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Clear search",
                            modifier = Modifier.clickable {
                                search = ""
                                kbController?.hide()
                            }
                        )
                    }
                },
                modifier = Modifier
                    .weight(.6f)
                    .padding(start = 10.dp, end = 20.dp)
            )
        }

        // Filter by tags
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = "Filter:",
                modifier = Modifier.padding(start = 10.dp)
            )

            // show all possible tags
            if (sharedViewModel.filterList.isEmpty()) {
                sharedViewModel.attenuatorTags.keys.forEach {
                    sharedViewModel.attenuatorTags[it]?.let { color ->
                        AddFilter(
                            filterEnabled = false,
                            filterColor = color,
                            type = it,
                            onClick = { type ->
                                sharedViewModel.addFilter(type)

                                when (type) {
                                    AttenuatorType.COAX -> coaxFilter = true
                                    AttenuatorType.PASSIVE -> passiveFilter = true
                                    AttenuatorType.DROP -> dropFilter = true
                                    AttenuatorType.PLANT -> plantFilter = true
                                }
                            }
                        )
                    }
                }
            }
            // show filters
            else {
                // coax tag
                sharedViewModel.attenuatorTags[AttenuatorType.COAX]?.let {
                    AddFilter(
                        filterEnabled = coaxFilter,
                        filterColor = it,
                        type = AttenuatorType.COAX,
                        onClick = {
                            if (coaxFilter)
                                sharedViewModel.removeFilter(it)
                            else
                                sharedViewModel.addFilter(it)

                            coaxFilter = !coaxFilter
                        }
                    )
                }

                // passive tag
                sharedViewModel.attenuatorTags[AttenuatorType.PASSIVE]?.let {
                    AddFilter(
                        filterEnabled = passiveFilter,
                        filterColor = it,
                        type = AttenuatorType.PASSIVE,
                        onClick = {
                            if (passiveFilter)
                                sharedViewModel.removeFilter(it)
                            else
                                sharedViewModel.addFilter(it)

                            passiveFilter = !passiveFilter
                        }
                    )
                }

                // drop tag
                sharedViewModel.attenuatorTags[AttenuatorType.DROP]?.let {
                    AddFilter(
                        filterEnabled = dropFilter,
                        filterColor = it,
                        type = AttenuatorType.DROP,
                        onClick = {
                            if (dropFilter)
                                sharedViewModel.removeFilter(it)
                            else
                                sharedViewModel.addFilter(it)

                            dropFilter = !dropFilter
                        }
                    )
                }

                // plant tag
                sharedViewModel.attenuatorTags[AttenuatorType.PLANT]?.let {
                    AddFilter(
                        filterEnabled = plantFilter,
                        filterColor = it,
                        type = AttenuatorType.PLANT,
                        onClick = {
                            if (plantFilter)
                                sharedViewModel.removeFilter(it)
                            else
                                sharedViewModel.addFilter(it)

                            plantFilter = !plantFilter
                        }
                    )
                }
            }
        }

        doSearch = search.isNotEmpty()
        dontSearch = search.isEmpty()
        dontFilter = sharedViewModel.filterList.isEmpty()
        doFilter = sharedViewModel.filterList.isNotEmpty()

        // if no tags and no search query, just copy the whole list
        if (dontFilter && dontSearch)
            showList = sharedViewModel.attenuatorMap.values.toMutableList()
        else {
            // iterate over all possible attenuators and add to showList
            // the ones that match search filters
            for (att in sharedViewModel.attenuatorMap.values) {

                if (doSearch && dontFilter) {
                    if (att.name().contains(search, ignoreCase = true)) {
                        if (!showList.contains(att))
                            showList.add(att)
                    }
                }

                else if (doSearch && doFilter) {
                    var matches = true

                    sharedViewModel.filterList.forEach {
                        if (!att.tags().contains(it))
                            matches = false
                    }

                    if (matches && att.name().contains(search, ignoreCase = true) &&
                        !showList.contains(att)) {

                        showList.add(att)

                    }
                }

                // tag filters only
                else {
                    var matches = true

                    sharedViewModel.filterList.forEach {
                        if (!att.tags().contains(it))
                            matches = false
                    }

                    if (matches && !showList.contains(att))
                        showList.add(att)

                }
            }
        }


        // display the list
        LazyColumn(
            contentPadding = PaddingValues(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = showList) { attenuator ->
                val card = AttenuatorCard(
                    attenuator.name(),
                    attenuator.tags(),
                    attenuator.isCoax()
                )

                AddAttenuatorCard(
                    card,
                    onClick = {
                        sharedViewModel.setAttenuatorCard(card)

                        // only show footage dialog if we are adding a coax attenuator
                        if (card.isCoax()) {
                            // logic for coax is in this function below in
                            // showLengthDialog conditional
                            showLengthDialog = true
                        } else {
                            // Find loss
                            for (data in sharedViewModel.attenuatorMap.values) {
                                if (data.name() == card.name()) {
                                    sharedViewModel.card!!.setLoss(
                                        getCableLoss(
                                            data,
                                            sharedViewModel.currentFreq.toInt(),
                                            0,
                                            sharedViewModel.currentTemp.toInt()
                                        )
                                    )

                                    break
                                }
                            }

                            // nav back to MainScreen
                            sharedViewModel.attenuatorCardList.add(card)
                            navController.navigate(Screen.Main.route)
                        }
                    },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }

    // Show footage dialog when adding coax attenuators
    if (showLengthDialog) {
        LengthDialog(
            label = "Enter length",
            defaultValue = "",
            onCancel = { showLengthDialog = false },
            onAdd = {
                sharedViewModel.addAttenuatorLength(it.toInt())

                // Find loss
                for (data in sharedViewModel.attenuatorMap.values) {
                    if (data.name() == sharedViewModel.card!!.name()) {
                        sharedViewModel.card!!.setLoss(
                            getCableLoss(
                                data,
                                sharedViewModel.currentFreq.toInt(),
                                sharedViewModel.getAttenuatorLength(),
                                sharedViewModel.currentTemp.toInt()
                            )
                        )

                        break
                    }
                }

                sharedViewModel.attenuatorCardList.add(
                    AttenuatorCard(
                        sharedViewModel.card!!.name(),
                        sharedViewModel.card!!.tags(),
                        sharedViewModel.card!!.isCoax(),
                        sharedViewModel.card!!.length(),
                        sharedViewModel.card!!.getLoss()
                    )
                )

                // Nav back to MainScreen
                navController.navigate(Screen.Main.route)
            }
        )
    }
}

/**************************************************************************
 * AddAttenuatorCard()
 *      Create Card for attenuator type
 **************************************************************************/
@Composable
private fun AddAttenuatorCard(
    card: AttenuatorCard,
    onClick:() -> Unit,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .clickable(onClick = { onClick() })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            // Name
            Text(
                text = card.name(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp, bottom = 6.dp, start = 1.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Tags
                card.tags().forEach {
                    val color =
                        when (it) {
                            AttenuatorType.COAX -> coaxColor
                            AttenuatorType.PASSIVE -> passiveColor
                            AttenuatorType.DROP -> dropColor
                            AttenuatorType.PLANT -> plantColor
                        }
                    AddAttenuatorTag(it, color, MaterialTheme.typography.bodySmall)
                }

            }        }
    }
}

/**************************************************************************
 * AddAttenuatorTag()
 *      Create small tags to describe each attenuator type.  This
 *      goes on each individual attenuator card (as opposed to the
 *      filter cards)
 **************************************************************************/
@Composable
private fun AddAttenuatorTag(
    type: AttenuatorType,
    color: Color,
    style: TextStyle,
) {
    Surface(
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = type.toString(),
            color = Color.White,
            style = style,
            modifier = Modifier
                .background(color)
                .padding(top = 0.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)
        )
    }
}

/**************************************************************************
 * AddTag()
 *      Add filter card.  Automatically colors card bgcolor to show
 *      whether it has been enabled or disabled.
 **************************************************************************/
@Composable
private fun AddFilter(
    filterEnabled: Boolean,
    filterColor: Color,
    type: AttenuatorType,
    onClick: (AttenuatorType) -> Unit
) {
    var tagBackgroundColor = Color.LightGray
    val tagFontStyle = MaterialTheme.typography.titleMedium

    if (filterEnabled)
        tagBackgroundColor = filterColor

    Surface(
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = type.toString(),
            color = Color.White,
            style = tagFontStyle,
            modifier = Modifier
                .background(tagBackgroundColor)
                .padding(horizontal = 6.dp, vertical = 3.dp)
                .clickable {
                    onClick(type)
                }
        )
    }
}

