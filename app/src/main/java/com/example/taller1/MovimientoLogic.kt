package com.example.taller1

fun indiceToPosicion(indice: Int): Pair<Int, Int>{

    val fila = indice / 3
    val columna = indice % 3
    return Pair(fila,columna)

}