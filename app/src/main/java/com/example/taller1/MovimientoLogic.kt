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

fun realizarMovimiento(tablero: List<Int>, indice1: Int,indice2: Int): List<Int>?{

    if(!sonAdyacentes(indice1,indice2)){
        return null
    }

    val tableroNuevo = tablero.toMutableList()
    val temp = tableroNuevo[indice1]
    tableroNuevo[indice1] = tableroNuevo[indice2]
    tableroNuevo[indice2] = temp

    return tableroNuevo
}

fun contadorMovimientos(actual: Int, exitoso: Boolean): Int{

    if(exitoso){
        return actual + 1
    }else{
        return actual
    }

}