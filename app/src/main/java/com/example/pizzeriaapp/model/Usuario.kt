package com.example.pizzeriaapp.model

open class Usuario(
    val nombre: String,
    val contacto: String
) {
    open fun describirRol(): String {
        return "Usuario"
    }
}