package com.example.pizzeriaapp.model

class Cliente(
    nombre: String,
    contacto: String,
    val direccion: String,
    val correo: String = "correo@invalido.com"
) : Usuario(nombre, contacto) {

    override fun describirRol(): String {
        return "Cliente"
    }

    // =====================================
    // COMPARACIÓN DE CLIENTES
    // =====================================

    override fun equals(other: Any?): Boolean {

        // Comprobar si es el mismo objeto
        if (this === other) {
            return true
        }

        // Comprobar que sea un Cliente
        if (other !is Cliente) {
            return false
        }

        // Comparar nombre y correo electrónico
        return nombre == other.nombre &&
               correo == other.correo
    }

    // =====================================
    // CÁLCULO DEL CÓDIGO HASH
    // =====================================

    override fun hashCode(): Int {

        var resultado = nombre.hashCode()

        resultado = 31 * resultado + correo.hashCode()

        return resultado
    }
    
    // =====================================
    // DESESTRUCTURACIÓN DE CLIENTES
    // =====================================

    operator fun component1(): String {
        return nombre
    }

    operator fun component2(): String {
        return correo
    }

    operator fun component3(): String {
        return contacto
    }
}