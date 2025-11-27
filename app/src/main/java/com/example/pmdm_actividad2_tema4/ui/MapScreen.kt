package com.example.pmdm_actividad2_tema4.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.pmdm_actividad2_tema4.modelos.TiendasData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(onNavigateBack: () -> Unit) {
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        val bounds = LatLngBounds.builder()
        TiendasData.tiendas.forEach { tienda ->
            bounds.include(LatLng(tienda.latitud, tienda.longitud))
        }
        cameraPositionState.move(
            CameraUpdateFactory.newLatLngBounds(bounds.build(), 100)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mapa de tiendas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState
        ) {
            TiendasData.tiendas.forEach { tienda ->
                Marker(
                    state = MarkerState(position = LatLng(tienda.latitud, tienda.longitud)),
                    title = tienda.nombre,
                    snippet = tienda.productoPrincipal,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            }
        }
    }
}
