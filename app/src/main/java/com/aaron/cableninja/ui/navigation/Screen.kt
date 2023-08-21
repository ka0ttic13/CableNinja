package com.aaron.cableninja.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object Add : Screen("add_screen")
}
