package com.aaron.cableninja.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aaron.cableninja.ui.navigation.BottomBarScreen
import com.aaron.cableninja.ui.navigation.BottomNavGraph
import com.aaron.cableninja.ui.theme.CableNinjaTheme
import com.aaron.cableninja.ui.viewmodels.mainViewModel

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CableNinjaTheme {
                val mainViewModel: mainViewModel = viewModel()
                //mainViewModel.clearAttenuatorList()

                // Lock screen orientation to portrait
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                navController = rememberNavController()

                createMainScaffold(
                    navController = navController,
                    mainViewModel = mainViewModel
                )

                BottomNavGraph(
                    mainViewModel = mainViewModel,
                    navController = navController
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun createMainScaffold(
    navController: NavController,
    mainViewModel: mainViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                // Home
                NavigationBar {
                    NavigationBarItem(
                        selected = mainViewModel.BOTTOMINDEX == 0,
                        onClick = {
                            mainViewModel.SETBOTTOMINDEX(0)

                            navController.navigate(BottomBarScreen.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },
                        label = {
                            Text(text = BottomBarScreen.Home.title)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (BottomBarScreen.Home.badgeCount != null) {
                                        Badge {
                                            Text(text = BottomBarScreen.Home.badgeCount.toString())
                                        }
                                    } else if (BottomBarScreen.Home.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                if (mainViewModel.BOTTOMINDEX == 0)
                                    Icon(
                                        imageVector = BottomBarScreen.Home.selectedIcon,
                                        contentDescription = BottomBarScreen.Home.title
                                    )
                                else
                                    Icon(
                                        imageVector = BottomBarScreen.Home.unselectedIcon,
                                        contentDescription = BottomBarScreen.Home.title
                                    )
                            }
                        }
                    )
                    // Add!
                    NavigationBarItem(
                        selected = mainViewModel.BOTTOMINDEX == 1,
                        onClick = {
                            mainViewModel.SETBOTTOMINDEX(1)

                            navController.navigate(BottomBarScreen.Add.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },
                        label = {
                            Text(text = BottomBarScreen.Add.title)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (BottomBarScreen.Home.badgeCount != null) {
                                        Badge {
                                            Text(text = BottomBarScreen.Home.badgeCount.toString())
                                        }
                                    } else if (BottomBarScreen.Home.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                if (mainViewModel.BOTTOMINDEX == 1)
                                    Icon(
                                        imageVector = BottomBarScreen.Add.selectedIcon,
                                        contentDescription = BottomBarScreen.Add.title
                                    )
                                else
                                    Icon(
                                        imageVector = BottomBarScreen.Add.unselectedIcon,
                                        contentDescription = BottomBarScreen.Add.title
                                    )
                            }
                        }
                    )
                    // Settings
                    NavigationBarItem(
                        selected = mainViewModel.BOTTOMINDEX == 2,
                        onClick = {
                            mainViewModel.SETBOTTOMINDEX(2)

                            navController.navigate(BottomBarScreen.Settings.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },
                        label = {
                            Text(text = BottomBarScreen.Settings.title)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (BottomBarScreen.Settings.badgeCount != null) {
                                        Badge {
                                            Text(text = BottomBarScreen.Settings.badgeCount.toString())
                                        }
                                    } else if (BottomBarScreen.Settings.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                if (mainViewModel.BOTTOMINDEX == 2)
                                    Icon(
                                        imageVector = BottomBarScreen.Settings.selectedIcon,
                                        contentDescription = BottomBarScreen.Settings.title
                                    )
                                else
                                    Icon(
                                        imageVector = BottomBarScreen.Settings.unselectedIcon,
                                        contentDescription = BottomBarScreen.Settings.title
                                    )
                            }
                        }
                    )
                }
            },
        ) {

        }
    }
}