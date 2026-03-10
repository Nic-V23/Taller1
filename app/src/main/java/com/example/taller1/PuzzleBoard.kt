package com.example.taller1
val TABLERO_RESUELTO = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)

data class PuzzleBoard(
    val casillas: List<Int>
)

fun generarTableroAleatorio(): List<Int> {
    return (0..8).toList().shuffled()
}

fun esSolucionable(tablero: List<Int>): Boolean {
    val sinCero = tablero.filter { it != 0 }
    var inversiones = 0
    for (i in sinCero.indices) {
        for (j in i + 1 until sinCero.size) {
            if (sinCero[i] > sinCero[j]) inversiones++
        }
    }
    return inversiones % 2 == 0
}

fun reiniciarTablero(): List<Int> {
    var tablero: List<Int>
    do {
        tablero = generarTableroAleatorio()
    } while (!esSolucionable(tablero))
    return tablero
}



