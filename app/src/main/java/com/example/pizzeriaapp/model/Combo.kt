package com.example.pizzeriaapp.model

@Promocionable("Combo elegible para promociones especiales")
class Combo(
    val nombre: String,
    val productos: List<Producto>
) {

    fun calcularPrecio(): Int {
        return productos.sumOf { it.precio }
    }
}