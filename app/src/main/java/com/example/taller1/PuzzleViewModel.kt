import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PuzzleViewModel : ViewModel() {


    private val _tablero = MutableStateFlow(reiniciarTablero())
    val tablero: StateFlow<List<Int>> = _tablero.asStateFlow()

    private val _celdaSeleccionada = MutableStateFlow<Int?>(null)
    val celdaSeleccionada: StateFlow<Int?> = _celdaSeleccionada.asStateFlow()

    private val _movimientos = MutableStateFlow(0)
    val movimientos: StateFlow<Int> = _movimientos.asStateFlow()

    private val _juegoGanado = MutableStateFlow(false)
    val juegoGanado: StateFlow<Boolean> = _juegoGanado.asStateFlow()

    fun seleccionarCelda(indice: Int) {
        val primera = _celdaSeleccionada.value

        if (primera == null) {
            _celdaSeleccionada.value = indice
        } else {
            if (primera == indice) {
                _celdaSeleccionada.value = null
                return
            }

            val nuevoTablero = realizarMovimiento(_tablero.value, primera, indice)

            if (nuevoTablero != null) {
                _movimientos.value = contadorMovimientos(_movimientos.value, true)
                _tablero.value = nuevoTablero
                _juegoGanado.value = (nuevoTablero == TABLERO_RESUELTO)
            } else {
                _movimientos.value = contadorMovimientos(_movimientos.value, false)
                _celdaSeleccionada.value = indice
                return
            }

            _celdaSeleccionada.value = null
        }
    }
    fun reiniciar() {
        _tablero.value = reiniciarTablero()
        _celdaSeleccionada.value = null
        _movimientos.value = 0
        _juegoGanado.value = false
    }
}