package com.example.pizzeriaapp.model

@Promocionable("Pizza elegible para promociones especiales")
class Producto(
    val nombre: String,
    val precio: Int,
    val ingredientes: Map<String, Int>,
    val categoria: String = "Pizza"
) {

    fun detallarProducto(): String {

        val detalleIngredientes = ingredientes.entries.joinToString(", ") {
            (ingrediente, cantidad) -> "$ingrediente ($cantidad)"
        }

        return "Pizza: $nombre | Precio: $$precio | Ingredientes: $detalleIngredientes"
    }
    
    // =====================================
    // COMPARACIÓN DE PRODUCTOS
    // =====================================

    override fun equals(other: Any?): Boolean {

        // Verificar si se trata del mismo objeto
        if (this === other) {
            return true
        }

        // Verificar que el otro objeto sea un Producto
        if (other !is Producto) {
            return false
        }

        // Comparar nombre y categoría
        return nombre == other.nombre &&
            categoria == other.categoria
    }


    // =====================================
    // CÁLCULO DEL CÓDIGO HASH
    // =====================================

    override fun hashCode(): Int {

        var resultado = nombre.hashCode()

        resultado = 31 * resultado + categoria.hashCode()

        return resultado
    }
}