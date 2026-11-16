package com.example.pizzeriaapp.model

// Anotación personalizada para identificar productos promocionables

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Promocionable(
    val descripcion: String = "Producto elegible para promociones"
)