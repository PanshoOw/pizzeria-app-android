package com.example.pizzeriaapp.logic

import com.example.pizzeriaapp.model.Cliente
import com.example.pizzeriaapp.model.Pedido
import com.example.pizzeriaapp.model.Producto
import com.example.pizzeriaapp.model.Repartidor
import java.time.LocalDate

// =====================================
// DEMOSTRACIÓN DEL SISTEMA DE PEDIDOS
// =====================================

fun procesarPedidoEjemplo(): String {

    // Crear cliente de prueba
    val cliente = Cliente(
        nombre = "Cliente de prueba",
        contacto = "+56 (9) 1234-5678",
        direccion = "Santiago",
        correo = "cliente@ejemplo.com"
    )

    // Crear pizza utilizando nuestra clase original
    val pizza = Producto(
        nombre = "Pepperoni con bordes de queso",
        precio = 10000,
        ingredientes = mapOf(
            "Masa" to 1,
            "Salsa de tomate" to 1,
            "Queso" to 2,
            "Pepperoni" to 1
        )
    )

    // Inventario de demostración
    val inventario = mutableMapOf(
        "Masa" to 10,
        "Salsa de tomate" to 10,
        "Queso" to 20,
        "Pepperoni" to 10
    )

    // Crear pedido con cuatro pizzas
    val pedido = Pedido(
        id = 1,
        cliente = cliente,
        productos = List(4) { pizza },
        fecha = LocalDate.of(2026, 11, 15)
    )

    // Verificar inventario y procesar pedido
    val resultadoInventario =
        verificarYActualizarInventario(pedido, inventario)

    // =====================================
    // ASIGNACIÓN DE REPARTIDORES
    // =====================================

    // Crear repartidores de ejemplo
    val repartidorPrincipal = Repartidor(
        nombre = "Carlos González",
        contacto = "Contacto interno 01",
        distanciaKm = 2.0
    )

    val repartidorAlternativo = Repartidor(
        nombre = "Pedro Ramírez",
        contacto = "Contacto interno 02",
        distanciaKm = 4.0
    )

    // Agrupar los repartidores
    val repartidores = listOf(
        repartidorPrincipal,
        repartidorAlternativo
    )

    // Buscar al repartidor disponible más cercano
    val repartidorAsignado = if (pedido.estado == "En preparación") {

        repartidores
            .sortedBy { it.distanciaKm }
            .find { it.disponible }

    } else {
        null
    }

    // Generar el resultado de la asignación
    val mensajeRepartidor = repartidorAsignado?.entregarPedido(pedido.id)
        ?: "No se asignó repartidor al pedido."

    // =====================================
    // NOTIFICACIÓN AL CLIENTE
    // =====================================

    val mensajeNotificacion = notificarCliente(pedido)

    // =====================================
    // GENERACIÓN DEL REPORTE DIARIO
    // =====================================

    val pedidosDelDia = arrayOf(pedido)

    val reporteDiario = generarReporteDiario(pedidosDelDia)

    // Preparar información para mostrar en Android
    return """
        ===== PEDIDO DE PRUEBA =====
    
        Cliente: ${cliente.nombre}
        Producto: ${pizza.nombre}
        Cantidad: ${pedido.cantidadPizzas}
        Fecha: ${pedido.fecha}
    
        Estado: ${pedido.estado}
    
        Subtotal: ${pedido.calcularSubtotal()} CLP
        Descuento por cantidad: ${pedido.calcularDescuento()} CLP
        Descuento promocional: ${pedido.calcularDescuentoPromocional()} CLP
        Impuestos: ${pedido.calcularImpuestos()} CLP
    
        TOTAL: ${pedido.calcularTotal()} CLP
    
        $resultadoInventario
    
        ===== REPARTIDOR ASIGNADO =====
    
        $mensajeRepartidor
        
        ===== NOTIFICACIÓN AL CLIENTE =====

        $mensajeNotificacion
        
        ===== REPORTE DEL DÍA =====

        $reporteDiario
    """.trimIndent()
}