package com.example.pmdm_actividad2_tema4.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    var acelerometro by remember { mutableStateOf(Triple(0f, 0f, 0f)) }
    var giroscopio by remember { mutableStateOf(Triple(0f, 0f, 0f)) }
    var luz by remember { mutableFloatStateOf(0f) }

    DisposableEffect(Unit) {
        val acelerometroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val giroscopioSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val luzSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    when (it.sensor.type) {
                        Sensor.TYPE_ACCELEROMETER -> {
                            acelerometro = Triple(it.values[0], it.values[1], it.values[2])
                        }
                        Sensor.TYPE_GYROSCOPE -> {
                            giroscopio = Triple(it.values[0], it.values[1], it.values[2])
                        }
                        Sensor.TYPE_LIGHT -> {
                            luz = it.values[0]
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        acelerometroSensor?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        giroscopioSensor?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        luzSensor?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sensores") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SensorCard(
                titulo = "Acelerómetro",
                x = acelerometro.first,
                y = acelerometro.second,
                z = acelerometro.third
            )

            SensorCard(
                titulo = "Giroscopio",
                x = giroscopio.first,
                y = giroscopio.second,
                z = giroscopio.third
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Sensor de Luz",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "%.2f lux".format(luz),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun SensorCard(titulo: String, x: Float, y: Float, z: Float) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "X: %.2f".format(x), style = MaterialTheme.typography.titleLarge)
            Text(text = "Y: %.2f".format(y), style = MaterialTheme.typography.titleLarge)
            Text(text = "Z: %.2f".format(z), style = MaterialTheme.typography.titleLarge)
        }
    }
}
