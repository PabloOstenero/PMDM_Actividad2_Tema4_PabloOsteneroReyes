package com.example.pmdm_actividad2_tema4.modelos

import com.example.pmdm_actividad2_tema4.R

data class Tienda(
    val nombre: String,
    val calle: String,
    val productoPrincipal: String,
    val latitud: Double,
    val longitud: Double,
    val descripcion: String,
    val oferta: Boolean,
    val foto: Int
)

object TiendasData {
    val tiendas = listOf(
        Tienda(
            nombre = "Bodega La Manzanilla",
            calle = "Calle Feduchy, 15",
            productoPrincipal = "Vinos y licores",
            latitud = 36.5270,
            longitud = -6.2885,
            descripcion = "Bodega tradicional gaditana con los mejores vinos de la tierra y tapas caseras",
            oferta = true,
            foto = R.drawable.tienda1
        ),
        Tienda(
            nombre = "Ultramarinos El Faro",
            calle = "Calle San Francisco, 23",
            productoPrincipal = "Productos gourmet",
            latitud = 36.5298,
            longitud = -6.2920,
            descripcion = "Tienda especializada en productos selectos de Cádiz y delicatessen",
            oferta = false,
            foto = R.drawable.tienda2
        ),
        Tienda(
            nombre = "Pescadería Bahía",
            calle = "Mercado Central",
            productoPrincipal = "Pescado fresco",
            latitud = 36.5310,
            longitud = -6.2945,
            descripcion = "El mejor pescado fresco de la bahía de Cádiz directo del mar",
            oferta = true,
            foto = R.drawable.tienda3
        ),
        Tienda(
            nombre = "Confitería La Perla",
            calle = "Plaza de las Flores, 8",
            productoPrincipal = "Dulces artesanales",
            latitud = 36.5325,
            longitud = -6.2910,
            descripcion = "Dulces tradicionales y tocinos de cielo elaborados de forma artesanal",
            oferta = false,
            foto = R.drawable.tienda4
        ),
        Tienda(
            nombre = "Librería Cervantes",
            calle = "Calle Ancha, 45",
            productoPrincipal = "Libros y papelería",
            latitud = 36.5340,
            longitud = -6.2895,
            descripcion = "La librería más antigua de Cádiz con una gran selección literaria",
            oferta = true,
            foto = R.drawable.tienda5
        ),
        Tienda(
            nombre = "Especias Gaditanas",
            calle = "Calle Sacramento, 12",
            productoPrincipal = "Especias y condimentos",
            latitud = 36.5285,
            longitud = -6.2905,
            descripcion = "Amplio surtido de especias, hierbas aromáticas y condimentos del mundo",
            oferta = false,
            foto = R.drawable.tienda6
        )
    )
}
