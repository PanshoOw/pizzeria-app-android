package com.example.pizzeriaapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.pizzeriaapp.data.RegistroPizzeria
import com.example.pizzeriaapp.data.ResumenPizzeria
import com.example.pizzeriaapp.model.Cliente
import com.example.pizzeriaapp.model.Pedido

// =====================================
// VIEWMODEL PRINCIPAL DE LA PIZZERÍA
// =====================================

class PizzeriaViewModel : ViewModel() {

    // Registro de clientes y pedidos
    private val registroPizzeria = RegistroPizzeria()

    // Estado interno del resumen
    private val _resumen = MutableLiveData(
        registroPizzeria.obtenerResumen()
    )

    // Estado observable desde la interfaz
    val resumen: LiveData<ResumenPizzeria> = _resumen

    // =====================================
    // REGISTRAR CLIENTES
    // =====================================

    fun registrarCliente(
        nombre: String,
        contacto: String,
        direccion: String,
        correo: String
    ): String? {

        val cliente = Cliente(
            nombre = nombre.trim(),
            contacto = contacto.trim(),
            direccion = direccion.trim(),
            correo = correo.trim()
        )

        val resultado = registroPizzeria.registrarCliente(cliente)

        if (resultado == null) {
            actualizarResumen()
        }

        return resultado
    }

    // =====================================
    // REGISTRAR PEDIDOS
    // =====================================

    fun registrarPedido(pedido: Pedido): String? {

        val resultado = registroPizzeria.registrarPedido(pedido)

        if (resultado == null) {
            actualizarResumen()
        }

        return resultado
    }

    // =====================================
    // REGISTRAR PEDIDO DESDE LA INTERFAZ
    // =====================================

    fun registrarPedido(
        correoCliente: String,
        cantidad: Int
    ): String? {

        val resultado = registroPizzeria.registrarPedido(
            correoCliente = correoCliente,
            cantidad = cantidad
        )

        if (resultado == null) {
            actualizarResumen()
        }

        return resultado
    }

    // =====================================
    // CONSULTAR CLIENTES
    // =====================================

    fun obtenerClientes(): List<Cliente> {

        return registroPizzeria.obtenerClientes()
    }

    // =====================================
    // ACTUALIZAR RESUMEN
    // =====================================

    private fun actualizarResumen() {

        _resumen.value = registroPizzeria.obtenerResumen()
    }
}