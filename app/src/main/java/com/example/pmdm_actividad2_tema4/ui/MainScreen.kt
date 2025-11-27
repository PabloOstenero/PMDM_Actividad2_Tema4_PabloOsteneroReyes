package com.example.pmdm_actividad2_tema4.ui

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.example.pmdm_actividad2_tema4.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.pmdm_actividad2_tema4.modelos.Tienda
import com.example.pmdm_actividad2_tema4.modelos.TiendasData
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToMap: () -> Unit,
    onNavigateToSensors: () -> Unit
) {
    val context = LocalContext.current
    val mediaPlayerCaja = remember { MediaPlayer.create(context, R.raw.caja_registradora) }
    val mediaPlayerExplosion = remember { MediaPlayer.create(context, R.raw.explosion) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayerCaja.release()
            mediaPlayerExplosion.release()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tiendas de Cádiz") },
                actions = {
                    IconButton(onClick = onNavigateToMap) {
                        Icon(Icons.Default.Place, contentDescription = "Mapa")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = onNavigateToSensors) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sensores")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(TiendasData.tiendas) { tienda ->
                TiendaCard(
                    tienda = tienda,
                    onSwipeRight = { mediaPlayerCaja.start() },
                    onSwipeLeft = { mediaPlayerExplosion.start() }
                )
            }
        }
    }
}

@Composable
fun TiendaCard(
    tienda: Tienda,
    onSwipeRight: () -> Unit,
    onSwipeLeft: () -> Unit
) {
    var modoDetalle by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val transition = updateTransition(targetState = modoDetalle, label = "card_transition")

    val cardScale by transition.animateFloat(label = "scale") { detalle ->
        if (detalle) 1.05f else 1f
    }

    val shadowColor by transition.animateColor(label = "shadow") { detalle ->
        if (detalle) Color.Red else Color.Gray
    }

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offer_scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .scale(if (tienda.oferta) scale else 1f)
            .scale(cardScale)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp), spotColor = shadowColor)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            if (offsetX.value > 300f) {
                                onSwipeRight()
                                offsetX.animateTo(
                                    targetValue = 600f,
                                    animationSpec = tween(200)
                                )
                                offsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            } else if (offsetX.value < -300f) {
                                onSwipeLeft()
                                offsetX.animateTo(
                                    targetValue = -600f,
                                    animationSpec = tween(200)
                                )
                                offsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            } else {
                                offsetX.animateTo(0f)
                            }
                        }
                    }
                ) { _, dragAmount ->
                    coroutineScope.launch {
                        offsetX.snapTo(offsetX.value + dragAmount)
                    }
                }
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = tienda.foto),
                contentDescription = tienda.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = tienda.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (tienda.oferta) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "OFERTA",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Red, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = tienda.calle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Text(
                    text = tienda.productoPrincipal,
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic
                )

                AnimatedVisibility(
                    visible = modoDetalle,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Text(
                        text = tienda.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = { modoDetalle = !modoDetalle },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(if (modoDetalle) "Ocultar" else "Ver detalles")
                }
            }
        }
    }
}
