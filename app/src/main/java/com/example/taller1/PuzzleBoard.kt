package com.example.taller1


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



