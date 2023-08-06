package com.aaron.cableninja

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AddScreen : Screen("add_screen")
}
