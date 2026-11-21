package com.example.pizzeriaapp.logic

import com.example.pizzeriaapp.model.Pedido

// =====================================
// NOTIFICACIONES A CLIENTES
// =====================================

fun notificarCliente(pedido: Pedido): String {

    // Comprobar que el pedido esté confirmado
    if (pedido.estado == "Pendiente" ||
        pedido.estado == "No confirmado"
    ) {
        return "Notificación no generada: pedido no confirmado."
    }

    // Obtener teléfono válido del cliente
    val telefonoValido: String? = pedido.cliente.contacto
        .takeIf {
            it.isNotBlank() && it != "No informado"
        }

    // Generar notificación simulada
    return telefonoValido?.let { telefono ->

        "Notificación simulada al $telefono: " +
                "su pedido N.º ${pedido.id} está ${pedido.estado.lowercase()}."

    } ?: "Notificación no generada: teléfono no válido."
}