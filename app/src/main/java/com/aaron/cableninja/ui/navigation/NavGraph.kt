package com.aaron.cableninja.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aaron.cableninja.ui.viewmodels.mainViewModel
import com.aaron.cableninja.ui.addscreen.AddScreen
import com.aaron.cableninja.ui.mainscreen.MainScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    // ViewModel for sharing data between screens
    val mainViewModel: mainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(navController, mainViewModel)
        }
        composable(route = Screen.Add.route) {
            AddScreen(navController, mainViewModel)
        }
    }
}