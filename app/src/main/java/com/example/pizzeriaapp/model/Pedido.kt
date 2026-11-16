package com.example.pizzeriaapp.model

import java.time.LocalDate

import com.example.pizzeriaapp.logic.estaEnPeriodoPromocional
import com.example.pizzeriaapp.logic.esPromocionable
import com.example.pizzeriaapp.logic.PORCENTAJE_PROMOCION_FECHA

private const val TASA_IMPUESTO = 19

class Pedido(
    val id: Int,
    val cliente: Cliente,
    val productos: List<Producto>,
    val notas: String? = null,
    val fecha: LocalDate = LocalDate.now()
) {

    var estado: String = "Pendiente"
        private set

    val cantidadPizzas: Int
        get() = productos.size

    // Calcular subtotal
    fun calcularSubtotal(): Int {
        return productos.sumOf { it.precio }
    }

    // Aplicar descuento solo a pedidos confirmados
    fun calcularDescuento(): Int {

        return when (estado) {

            "En preparación", "Listo", "En camino", "Entregado" -> {
                if (cantidadPizzas > 3) {
                    calcularSubtotal() * 10 / 100
                } else {
                    0
                }
            }

            else -> 0
        }
    }
    
    // Calcular descuento sobre productos promocionables
    fun calcularDescuentoPromocional(): Int {

        // Comprobar que el pedido esté confirmado
        if (estado !in listOf(
                "En preparación",
                "Listo",
                "En camino",
                "Entregado"
            )
        ) {
            return 0
        }

        // Comprobar que la fecha esté dentro del período promocional
        if (!estaEnPeriodoPromocional(fecha)) {
            return 0
        }

        // Identificar productos promocionables mediante Reflection
        val productosElegibles = productos.filter {
            esPromocionable(it)
        }

        // Calcular subtotal de los productos elegibles
        val subtotalElegible = productosElegibles.sumOf {
            it.precio
        }

        // Aplicar primero el descuento por cantidad, si corresponde
        val descuentoCantidadElegible = if (cantidadPizzas > 3) {
            subtotalElegible * 10 / 100
        } else {
            0
        }

        // Calcular la base del descuento promocional
        val basePromocional =
            subtotalElegible - descuentoCantidadElegible

        // Aplicar el descuento promocional del 5 %
        return basePromocional * PORCENTAJE_PROMOCION_FECHA / 100
    }
    
    // Calcular impuestos sobre el subtotal después del descuento
    fun calcularImpuestos(): Int {

        val baseImponible: Int =
            calcularSubtotal() -
            calcularDescuento() -
            calcularDescuentoPromocional()

        return when (estado) {

            "En preparación", "Listo", "En camino", "Entregado" ->
                baseImponible * TASA_IMPUESTO / 100

            else -> 0
        }
    }

    // Calcular el total incluyendo descuentos e impuestos
    fun calcularTotal(): Int {

        return when (estado) {

            "En preparación", "Listo", "En camino", "Entregado" ->
                calcularSubtotal() -
                calcularDescuento() -
                calcularDescuentoPromocional() +
                calcularImpuestos()

            else -> 0
        }
    }
    
    // =====================================
    // SOBRECARGA DEL OPERADOR +
    // =====================================

    operator fun plus(otroPedido: Pedido): Pedido {

        // Verificar que los pedidos correspondan al mismo cliente
        require(cliente == otroPedido.cliente) {
            "No se pueden combinar pedidos de clientes diferentes."
        }

        // Verificar que pertenezcan a la misma fecha
        require(fecha == otroPedido.fecha) {
            "No se pueden combinar pedidos de fechas diferentes."
        }

        // Crear un nuevo pedido con los productos de ambos
        val pedidoCombinado = Pedido(
            id = maxOf(id, otroPedido.id) + 1,
            cliente = cliente,
            productos = productos + otroPedido.productos,
            notas = listOfNotNull(notas, otroPedido.notas)
                .joinToString(" | ")
                .takeIf { it.isNotBlank() },
            fecha = fecha
        )

        // Conservar la condición de confirmado si ambos pedidos lo están
        val estadosConfirmados = setOf(
            "En preparación",
            "Listo",
            "En camino",
            "Entregado"
        )

        if (estado in estadosConfirmados &&
            otroPedido.estado in estadosConfirmados
        ) {
            pedidoCombinado.cambiarEstado("En preparación")
        }

        return pedidoCombinado
    }

    // Actualizar estado del pedido
    fun cambiarEstado(nuevoEstado: String) {
        estado = nuevoEstado
    }
    
    // =====================================
    // DESESTRUCTURACIÓN DE PEDIDOS
    // =====================================

    operator fun component1(): Cliente {
        return cliente
    }

    operator fun component2(): List<Producto> {
        return productos
    }

    operator fun component3(): Int {
        return calcularTotal()
    }
}