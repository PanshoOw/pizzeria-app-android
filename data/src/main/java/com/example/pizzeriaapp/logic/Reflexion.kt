package com.example.pizzeriaapp.logic

import com.example.pizzeriaapp.model.Promocionable

// =====================================
// IDENTIFICAR ELEMENTOS PROMOCIONABLES
// =====================================

fun esPromocionable(elemento: Any): Boolean {

    return elemento.javaClass.isAnnotationPresent(
        Promocionable::class.java
    )
}


// =====================================
// INSPECCIONAR CLASES MEDIANTE REFLECTION
// =====================================

fun inspeccionarClase(elemento: Any) {

    val clase = elemento.javaClass

    println("\n===== REFLECTION: ${clase.simpleName} =====")

    // Consultar la anotación personalizada
    val anotacion = clase.getAnnotation(
        Promocionable::class.java
    )

    println(
        "Promocionable: ${anotacion?.descripcion ?: "No"}"
    )

    // Listar propiedades
    println("\nPropiedades:")

    for (propiedad in clase.declaredFields) {

        if (!propiedad.isSynthetic) {
            println("- ${propiedad.name}")
        }
    }

    // Listar métodos
    println("\nMétodos:")

    for (metodo in clase.declaredMethods) {

        if (!metodo.isSynthetic) {
            println("- ${metodo.name}")
        }
    }
}