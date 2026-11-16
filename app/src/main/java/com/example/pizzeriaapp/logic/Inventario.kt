package com.example.pizzeriaapp.logic

import com.example.pizzeriaapp.model.Pedido

fun verificarYActualizarInventario(
    pedido: Pedido,
    inventario: MutableMap<String, Int>
): String {

    // Calcular los ingredientes necesarios para todas las pizzas
    val ingredientesNecesarios = mutableMapOf<String, Int>()

    for (producto in pedido.productos) {
        for ((ingrediente, cantidad) in producto.ingredientes) {

            ingredientesNecesarios[ingrediente] =
                (ingredientesNecesarios[ingrediente] ?: 0) + cantidad
        }
    }

    // Identificar los ingredientes que no alcanzan
    val ingredientesFaltantes = ingredientesNecesarios.filter {
        (ingrediente, cantidadNecesaria) ->

        (inventario[ingrediente] ?: 0) < cantidadNecesaria
    }

    // Verificar disponibilidad y procesar el pedido
    return when {

        pedido.productos.isEmpty() -> {
            pedido.cambiarEstado("No confirmado")

            "Error: el pedido debe contener al menos una pizza."
        }

        ingredientesFaltantes.isNotEmpty() -> {
            pedido.cambiarEstado("No confirmado")

            val detalleFaltantes = ingredientesFaltantes.entries.joinToString("\n") {
                (ingrediente, cantidadNecesaria) ->

                val cantidadDisponible = inventario[ingrediente] ?: 0
                val cantidadFaltante = cantidadNecesaria - cantidadDisponible

                "- $ingrediente: faltan $cantidadFaltante unidades."
            }

            """
            Stock insuficiente. Pedido no confirmado.

            Ingredientes faltantes:
            $detalleFaltantes

            Pasos para reabastecer:
            1. Revisar los ingredientes faltantes.
            2. Reponer las cantidades necesarias.
            3. Actualizar el inventario.
            4. Intentar procesar nuevamente el pedido.
            """.trimIndent()
        }

        else -> {

            // Descontar los ingredientes utilizados
            for ((ingrediente, cantidad) in ingredientesNecesarios) {

                inventario[ingrediente] =
                    (inventario[ingrediente] ?: 0) - cantidad
            }

            pedido.cambiarEstado("En preparación")

            "Pedido confirmado. Inventario actualizado correctamente."
        }
    }
}