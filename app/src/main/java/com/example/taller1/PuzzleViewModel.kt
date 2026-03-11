package com.example.taller1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PuzzleViewModel : ViewModel() {

    private val _tablero = MutableStateFlow<List<Int>>(reiniciarTablero())
    val tablero: StateFlow<List<Int>> = _tablero.asStateFlow()

    private val _celdaSeleccionada = MutableStateFlow<Int?>(null)
    val celdaSeleccionada: StateFlow<Int?> = _celdaSeleccionada.asStateFlow()

    private val _movimientos = MutableStateFlow(0)
    val movimientos: StateFlow<Int> = _movimientos.asStateFlow()

    private val _juegoGanado = MutableStateFlow(false)
    val juegoGanado: StateFlow<Boolean> = _juegoGanado.asStateFlow()

    private val _metaMinima = MutableStateFlow(0)
    val metaMinima: StateFlow<Int> = _metaMinima.asStateFlow()

    var tableroInicial: List<Int> = _tablero.value
        private set

    fun seleccionarCelda(indice: Int) {
        if (_juegoGanado.value) return

        val seleccionPrevia = _celdaSeleccionada.value

        if (seleccionPrevia == null) {
            _celdaSeleccionada.value = indice
        } else {
            if (seleccionPrevia == indice) {
                _celdaSeleccionada.value = null
                return
            }

            val tableroActual = _tablero.value
            val nuevoTablero = realizarMovimiento(tableroActual, seleccionPrevia, indice)

            if (nuevoTablero != null) {
                _tablero.value = nuevoTablero
                _movimientos.value = contadorMovimientos(_movimientos.value, exitoso = true)

                if (estaResuelto(nuevoTablero)) {
                    _metaMinima.value = calcularMetaMinima(tableroInicial)
                    _juegoGanado.value = true
                }
            } else {
                _celdaSeleccionada.value = indice
                return
            }
            _celdaSeleccionada.value = null
        }
    }

    fun reiniciar() {
        val nuevoTablero = reiniciarTablero()
        tableroInicial = nuevoTablero
        _tablero.value = nuevoTablero
        _celdaSeleccionada.value = null
        _movimientos.value = 0
        _juegoGanado.value = false
        _metaMinima.value = 0
    }
}