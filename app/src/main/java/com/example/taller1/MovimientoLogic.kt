package com.example.taller1

fun indiceToPosicion(indice: Int): Pair<Int, Int>{

    val fila = indice / 3
    val columna = indice % 3
    return Pair(fila,columna)

}

fun sonAdyacentes(indice1: Int, indice2: Int): Boolean {

    val posicion1 =indiceToPosicion(indice1)
    val posicion2 =indiceToPosicion(indice2)

    val mismaFila = posicion1.first == posicion2.first
    val mismaColumna = posicion1.second == posicion2.second

    val filaDiferencia = Math.abs(posicion1.first - posicion2.first)
    val columnaDiferencia = Math.abs(posicion1.second - posicion2.second)

    return(mismaFila && columnaDiferencia == 1) || (mismaColumna && filaDiferencia == 1)
}


