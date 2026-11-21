package com.example.pizzeriaapp.logic

import java.time.LocalDate

// =====================================
// CONFIGURACIÓN DE PROMOCIONES
// =====================================

const val PORCENTAJE_PROMOCION_FECHA = 5

private val inicioPromocion = LocalDate.of(2026, 11, 1)
private val finPromocion = LocalDate.of(2026, 11, 30)

// =====================================
// VERIFICAR PERÍODO PROMOCIONAL
// =====================================

fun estaEnPeriodoPromocional(fecha: LocalDate): Boolean {

    return fecha in inicioPromocion..finPromocion
}