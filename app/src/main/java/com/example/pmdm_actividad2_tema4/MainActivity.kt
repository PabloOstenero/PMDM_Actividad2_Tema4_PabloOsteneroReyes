package com.example.pmdm_actividad2_tema4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pmdm_actividad2_tema4.navegation.Screen
import com.example.pmdm_actividad2_tema4.ui.MainScreen
import com.example.pmdm_actividad2_tema4.ui.MapScreen
import com.example.pmdm_actividad2_tema4.ui.SensorsScreen
import com.example.pmdm_actividad2_tema4.ui.theme.Pmdm_actividad2_tema4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pmdm_actividad2_tema4Theme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                ) {
                    composable(Screen.Main.route) {
                        MainScreen(
                            onNavigateToMap = { navController.navigate(Screen.Map.route) },
                            onNavigateToSensors = { navController.navigate(Screen.Sensors.route) }
                        )
                    }
                    composable(Screen.Map.route) {
                        MapScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable(Screen.Sensors.route) {
                        SensorsScreen(onNavigateBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
