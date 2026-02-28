package com.example.taller1

data class PuzzleBoard(
    val casillas: List<Int>
)

val TABLERO_RESUELTO = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)

fun generarTableroAleatorio(): List<Int> {
    return (0..8).toList().shuffled()
}

fun esSolucionable(tablero: List<Int>): Boolean {
    val nums = tablero.filter { it != 0 }
    var inversiones = 0
    for (i in nums.indices) {
        for (j in i + 1 until nums.size) {
            if (nums[i] > nums[j]) inversiones++
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


