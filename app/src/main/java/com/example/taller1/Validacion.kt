package com.example.taller1

import java.util.LinkedList


val TABLERO_RESUELTO = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)


fun estaResuelto(tablero: List<Int>): Boolean {
    return tablero == TABLERO_RESUELTO
}

fun calcularMetaMinima(tableroInicial: List<Int>): Int {


    if (estaResuelto(tableroInicial)) return 0


    val cola = LinkedList<Pair<List<Int>, Int>>()


    val visitados = HashSet<String>()


    cola.add(Pair(tableroInicial, 0))
    visitados.add(tableroInicial.toString())

    while (cola.isNotEmpty()) {


        val (estadoActual, movimientos) = cola.poll()


        if (estadoActual == TABLERO_RESUELTO) {
            return movimientos
        }


        val vecinos = generarVecinos(estadoActual)

        for (vecino in vecinos) {
            val clave = vecino.toString()
            if (clave !in visitados) {
                visitados.add(clave)

                cola.add(Pair(vecino, movimientos + 1))
            }
        }

    }


    return -1
}

private fun generarVecinos(tablero: List<Int>): List<List<Int>> {
    val vecinos = mutableListOf<List<Int>>()


    val posVacia = tablero.indexOf(0)


    val fila = posVacia / 3
    val col  = posVacia % 3


    val movimientos = listOf(
        Pair(-1,  0),   // Arriba:    subir una fila
        Pair( 1,  0),   // Abajo:     bajar una fila
        Pair( 0, -1),   // Izquierda: columna - 1
        Pair( 0,  1)    // Derecha:   columna + 1
    )

    for ((dFila, dCol) in movimientos) {
        val nuevaFila = fila + dFila
        val nuevaCol  = col  + dCol


        if (nuevaFila in 0..2 && nuevaCol in 0..2) {
            val posDestino = nuevaFila * 3 + nuevaCol


            val nuevoTablero = tablero.toMutableList()
            nuevoTablero[posVacia]   = nuevoTablero[posDestino]
            nuevoTablero[posDestino] = 0

            vecinos.add(nuevoTablero)
        }
    }

    return vecinos
}


fun evaluarEficiencia(realizados: Int, meta: Int): String {
    val diferencia = realizados - meta

    return when {
        diferencia == 0        -> "¡Perfecto! Igualaste la meta "
        diferencia in 1..5     -> "¡Muy bien! Estuviste cerca "
        else                   -> "Puedes mejorar, superaste la meta "
    }
}