package com.aaron.cableninja.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aaron.cableninja.ui.navigation.BottomBarScreen
import com.aaron.cableninja.ui.navigation.BottomNavGraph
import com.aaron.cableninja.ui.theme.CableNinjaTheme
import com.aaron.cableninja.ui.viewmodels.mainViewModel

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CableNinjaTheme {
                val mainViewModel: mainViewModel = viewModel()

                val bottomBarItems = listOf(
                    BottomBarScreen.Home,
                    BottomBarScreen.Add,
                    BottomBarScreen.Settings
                )

                // Lock screen orientation to portrait
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                bottomBarItems.forEachIndexed { index, item ->
                                    // Home
                                    NavigationBarItem(
                                        selected = mainViewModel.BOTTOMINDEX == index,
                                        onClick = {
                                            mainViewModel.SETBOTTOMINDEX(index)

                                            if (mainViewModel.BOTTOMINDEX == 1)
                                                mainViewModel.clearFilters()

                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id)
                                                launchSingleTop = true
                                            }
                                        },
                                        label = {
                                            Text(text = item.title)
                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (item.badgeCount != null) {
                                                        Badge {
                                                            Text(text = item.badgeCount.toString())
                                                        }
                                                    } else if (item.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector =
                                                    if (index ==
                                                        mainViewModel.BOTTOMINDEX
                                                    ) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.title
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        navController = rememberNavController()
                        BottomNavGraph(navController = navController, mainViewModel = mainViewModel)
                    }
                }
            }
        }
    }
}

