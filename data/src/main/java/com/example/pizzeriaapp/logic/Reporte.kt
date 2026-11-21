package com.example.pizzeriaapp.logic

import com.example.pizzeriaapp.model.Pedido

// =====================================
// GENERACIÓN DEL REPORTE DIARIO
// =====================================

fun generarReporteDiario(pedidos: Array<Pedido>): String {

    return buildString {

        appendLine("=== REPORTE DIARIO DE PEDIDOS ===")

        // Recorrer todos los pedidos del día
        for (pedido in pedidos) {

            val detallePizzas = pedido.productos
                .groupBy { it.nombre to it.precio }
                .entries
                .joinToString(", ") { (producto, unidades) ->

                    "${producto.first} " +
                            "(x${unidades.size}, ${producto.second} CLP c/u)"
                }

            appendLine()
            appendLine("Pedido N.º ${pedido.id}")
            appendLine("Fecha: ${pedido.fecha}")
            appendLine("Cliente: ${pedido.cliente.nombre}")
            appendLine("Teléfono: ${pedido.cliente.contacto}")
            appendLine("Correo: ${pedido.cliente.correo}")
            appendLine("Pizzas: $detallePizzas")
            appendLine("Notas: ${pedido.notas ?: "Sin notas"}")

            appendLine("Subtotal: ${pedido.calcularSubtotal()} CLP")

            appendLine(
                "Descuento por cantidad: " +
                        "${pedido.calcularDescuento()} CLP"
            )

            appendLine(
                "Descuento promocional (5%): " +
                        "${pedido.calcularDescuentoPromocional()} CLP"
            )

            appendLine(
                "Impuestos (19%): ${pedido.calcularImpuestos()} CLP"
            )

            appendLine("Total: ${pedido.calcularTotal()} CLP")
            appendLine("Estado: ${pedido.estado}")
        }

        // Agrupar pedidos según su estado
        val completados = pedidos.filter {
            it.estado == "Entregado"
        }

        val pendientes = pedidos.filter {
            it.estado != "Entregado" &&
                    it.estado != "No confirmado"
        }

        val rechazados = pedidos.filter {
            it.estado == "No confirmado"
        }

        appendLine()
        appendLine("========== RESUMEN DIARIO ==========")
        appendLine("Total de pedidos: ${pedidos.size}")
        appendLine("Pedidos completados: ${completados.size}")
        appendLine("Pedidos pendientes: ${pendientes.size}")
        appendLine("Pedidos no confirmados: ${rechazados.size}")
    }
}