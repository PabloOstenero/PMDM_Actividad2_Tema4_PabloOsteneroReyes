package com.example.pmdm_actividad2_tema4.navegation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Map : Screen("map")
    object Sensors : Screen("sensors")
}
