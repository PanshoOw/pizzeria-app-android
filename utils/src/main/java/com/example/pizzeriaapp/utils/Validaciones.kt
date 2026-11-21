package com.example.pizzeriaapp.utils

// =====================================
// VALIDACIONES GENERALES
// =====================================

fun validarNombreCliente(nombre: String): String? {

    // Normalizar los caracteres Unicode.
    val nombreLimpio = java.text.Normalizer.normalize(
        nombre.trim(),
        java.text.Normalizer.Form.NFC
    )

    // Comprobar que exista un nombre.
    if (nombreLimpio.isBlank()) {
        return "El nombre completo es obligatorio."
    }

    // Separar el nombre completo en palabras.
    val palabras = nombreLimpio.split(
        Regex("""[\p{Z}\s]+""")
    )

    // Exigir al menos un nombre y un apellido.
    if (palabras.size < 2) {
        return "Debes ingresar al menos un nombre y un apellido."
    }

    // Aceptar letras Unicode, acentos, ñ y nombres compuestos.
    val patron = Regex(
        """^[\p{L}\p{M}]+(?:[-'’][\p{L}\p{M}]+)*$"""
    )

    // Comprobar que todas las palabras sean válidas.
    if (palabras.any { !patron.matches(it) }) {
        return "El nombre completo solo puede contener letras."
    }

    return null
}

fun validarContacto(contacto: String): String? {

    val contactoLimpio = contacto.trim()

    if (contactoLimpio.isBlank()) {
        return "El teléfono del cliente es obligatorio."
    }

    // Permitir números, espacios, paréntesis y guiones.
    val patron = Regex("""^\+?[0-9()\s-]+$""")

    if (!patron.matches(contactoLimpio)) {
        return "El teléfono contiene caracteres no permitidos."
    }

    // Obtener únicamente los dígitos.
    val digitos = contactoLimpio.filter { it.isDigit() }

    // Número móvil chileno sin código de país.
    val formatoNacional =
        digitos.length == 9 &&
                digitos.startsWith("9") &&
                !contactoLimpio.startsWith("+")

    // Número móvil chileno con código de país.
    val formatoInternacional =
        digitos.length == 11 &&
                digitos.startsWith("569")

    if (!formatoNacional && !formatoInternacional) {
        return "Ingresa un número móvil chileno válido."
    }

    return null
}

fun validarCorreo(correo: String): String? {

    val correoLimpio = correo.trim()

    if (correoLimpio.isBlank()) {
        return "El correo electrónico es obligatorio."
    }

    if (correoLimpio.length > 254) {
        return "El correo electrónico es demasiado largo."
    }

    val patron = Regex(
        """^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(?:\.[A-Za-z0-9-]+)+$"""
    )

    val usuario = correoLimpio.substringBefore("@")
    val dominio = correoLimpio.substringAfter("@", "")

    val etiquetasDominio = dominio.split(".")

    val dominioValido = etiquetasDominio.all {
        it.isNotEmpty() &&
                !it.startsWith("-") &&
                !it.endsWith("-")
    }

    if (!patron.matches(correoLimpio) ||
        correoLimpio.contains("..") ||
        usuario.startsWith(".") ||
        usuario.endsWith(".") ||
        usuario.length > 64 ||
        !dominioValido
    ) {
        return "El correo electrónico no tiene un formato válido."
    }

    return null
}

fun validarCantidad(cantidad: Int): String? {

    if (cantidad <= 0) {
        return "La cantidad debe ser mayor que cero."
    }

    return null
}