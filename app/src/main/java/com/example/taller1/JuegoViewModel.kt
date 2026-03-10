package com.example.taller1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


data class EstadoJuego(
    val tablero: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0),
    val movimientos: Int = 0,
    val juegoGanado: Boolean = false,
    val metaMinima: Int = 0,
    val calculandoMeta: Boolean = false
)

class JuegoViewModel : ViewModel() {

    private val _estado = MutableStateFlow(EstadoJuego())
    val estado: StateFlow<EstadoJuego> = _estado

    fun iniciarPartida(tableroInicial: List<Int>) {
        _estado.value = EstadoJuego(
            tablero = tableroInicial,
            calculandoMeta = true
        )

        viewModelScope.launch {
            val meta = withContext(Dispatchers.Default) {
                calcularMetaMinima(tableroInicial)
            }
            _estado.value = _estado.value.copy(
                metaMinima = meta,
                calculandoMeta = false
            )
        }
    }

    fun realizarMovimiento(nuevoTablero: List<Int>) {
        val movimientosActualizados = _estado.value.movimientos + 1
        val gano = estaResuelto(nuevoTablero)

        _estado.value = _estado.value.copy(
            tablero = nuevoTablero,
            movimientos = movimientosActualizados,
            juegoGanado = gano
        )
    }

    fun jugarDeNuevo(nuevoTablero: List<Int>) {
        iniciarPartida(nuevoTablero)
    }

    fun cerrarDialogo() {
        _estado.value = _estado.value.copy(juegoGanado = false)
    }
}


