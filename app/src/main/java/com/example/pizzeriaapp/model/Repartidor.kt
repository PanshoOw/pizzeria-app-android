package com.example.pizzeriaapp.model

class Repartidor(
    nombre: String,
    contacto: String,
    val distanciaKm: Double,
    var disponible: Boolean = true
) : Usuario(nombre, contacto) {

    override fun describirRol(): String {
        return "Repartidor"
    }

    fun entregarPedido(idPedido: Int): String {
        return "Entrega del pedido $idPedido asignada a $nombre."
    }
}