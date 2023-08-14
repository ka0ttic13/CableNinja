package com.aaron.cableninja.presentation.ui

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object Add : Screen("add_screen")
}
