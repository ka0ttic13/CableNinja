package com.aaron.cableninja.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aaron.cableninja.ui.viewmodels.mainViewModel
import com.aaron.cableninja.ui.addscreen.AddScreen
import com.aaron.cableninja.ui.homescreen.HomeScreen
import com.aaron.cableninja.ui.settingscreen.SettingsScreen

@Composable
fun BottomNavGraph(
    mainViewModel: mainViewModel,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController, mainViewModel)
        }
        composable(route = BottomBarScreen.Add.route) {
            AddScreen(navController, mainViewModel)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController, mainViewModel)
        }
    }
}