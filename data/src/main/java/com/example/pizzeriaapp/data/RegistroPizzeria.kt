package com.example.pizzeriaapp.data

import com.example.pizzeriaapp.model.Cliente
import com.example.pizzeriaapp.model.Pedido
import com.example.pizzeriaapp.utils.validarNombreCliente
import com.example.pizzeriaapp.utils.validarContacto
import com.example.pizzeriaapp.utils.validarCorreo
import com.example.pizzeriaapp.utils.validarCantidad
import com.example.pizzeriaapp.model.Producto
import com.example.pizzeriaapp.logic.verificarYActualizarInventario

// =====================================
// RESUMEN DE LA PIZZERÍA
// =====================================

data class ResumenPizzeria(
    val totalClientes: Int,
    val totalPedidos: Int,
    val ultimoCliente: String
)

// =====================================
// REGISTRO DE CLIENTES Y PEDIDOS
// =====================================

class RegistroPizzeria {

    private val clientes = mutableListOf<Cliente>()
    private val pedidos = mutableListOf<Pedido>()

    // Inventario compartido por los pedidos registrados.
    private val inventario = mutableMapOf(
        "Masa" to 10,
        "Salsa de tomate" to 10,
        "Queso" to 20,
        "Pepperoni" to 10
    )

    // Registrar un nuevo cliente
    fun registrarCliente(cliente: Cliente): String? {

        validarNombreCliente(cliente.nombre)?.let {
            return it
        }

        validarContacto(cliente.contacto)?.let {
            return it
        }

        validarCorreo(cliente.correo)?.let {
            return it
        }

        if (cliente.direccion.isBlank()) {
            return "La dirección del cliente es obligatoria."
        }

        // Verificar que el correo electrónico no esté registrado.
        val correoDuplicado = clientes.any {
            it.correo.trim().equals(
                cliente.correo.trim(),
                ignoreCase = true
            )
        }

        if (correoDuplicado) {
            return "El correo electrónico ya se encuentra registrado."
        }

        if (clientes.any { it == cliente }) {
            return "El cliente ya se encuentra registrado."
        }

        clientes.add(cliente)

        return null
    }

    // Crear un pedido para un cliente registrado.
    fun registrarPedido(
        correoCliente: String,
        cantidad: Int
    ): String? {

        // Validar la cantidad solicitada.
        validarCantidad(cantidad)?.let {
            return it
        }

        // Limitar la cantidad para esta demostración.
        if (cantidad > 10) {
            return "Se permiten como máximo 10 pizzas por pedido."
        }

        // Buscar al cliente por su correo electrónico.
        val cliente = clientes.firstOrNull {
            it.correo.equals(
                correoCliente.trim(),
                ignoreCase = true
            )
        } ?: return "No existe un cliente registrado con ese correo."

        // Producto disponible en esta demostración.
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

        // Generar el siguiente identificador.
        val siguienteId = (pedidos.maxOfOrNull { it.id } ?: 0) + 1

        // Crear el pedido con la cantidad solicitada.
        val pedido = Pedido(
            id = siguienteId,
            cliente = cliente,
            productos = List(cantidad) { pizza }
        )

        // Reutilizar la función de registro existente.
        return registrarPedido(pedido)
    }

    // Registrar un nuevo pedido
    fun registrarPedido(pedido: Pedido): String? {

        validarCantidad(pedido.cantidadPizzas)?.let {
            return it
        }

        if (clientes.none { it == pedido.cliente }) {
            return "El cliente del pedido no está registrado."
        }

        if (pedidos.any { it.id == pedido.id }) {
            return "Ya existe un pedido con ese identificador."
        }

        // Verificar y actualizar el inventario.
        val resultadoInventario = verificarYActualizarInventario(
            pedido,
            inventario
        )

        // No registrar pedidos que no pudieron confirmarse.
        if (pedido.estado != "En preparación") {
            return resultadoInventario
        }

        // Registrar el pedido confirmado.
        pedidos.add(pedido)

        return null
    }

    // Obtener el resumen actualizado
    fun obtenerResumen(): ResumenPizzeria {

        return ResumenPizzeria(
            totalClientes = clientes.size,
            totalPedidos = pedidos.size,
            ultimoCliente = clientes.lastOrNull()?.nombre
                ?: "Sin clientes registrados"
        )
    }

    // Obtener los registros sin exponer las listas internas
    fun obtenerClientes(): List<Cliente> {
        return clientes.toList()
    }

    fun obtenerPedidos(): List<Pedido> {
        return pedidos.toList()
    }
}