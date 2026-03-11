package com.example.taller1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller1.ui.theme.Taller1Theme
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Taller1Theme {
                PuzzleScreen()
            }
        }
    }
}

@Composable
fun PuzzleScreen() {
    // YARITZA — genera el tablero aleatorio solucionable
    var tablero by remember { mutableStateOf(reiniciarTablero()) }
    var tableroInicial by remember { mutableStateOf(tablero) }
    var movimientos by remember { mutableIntStateOf(0) }
    var juegoGanado by remember { mutableStateOf(false) }

    // PIPE — calcula la meta mínima al inicio
    val metaMinima = remember(tableroInicial) {
        calcularMetaMinima(tableroInicial)
    }

    // NICOLAS — lógica de movimiento al hueco
    fun moverPieza(indice: Int) {
        if (juegoGanado) return
        val posVacia = tablero.indexOf(0)
        val fila = indice / 3
        val columna = indice % 3
        val filaVacia = posVacia / 3
        val columnaVacia = posVacia % 3

        val esAdyacente = (fila == filaVacia && abs(columna - columnaVacia) == 1) ||
                (columna == columnaVacia && abs(fila - filaVacia) == 1)

        if (esAdyacente) {
            val nuevo = tablero.toMutableList()
            nuevo[posVacia] = nuevo[indice]
            nuevo[indice] = 0
            tablero = nuevo
            // NICOLAS — incrementa contador
            movimientos = contadorMovimientos(movimientos, exitoso = true)
            // PIPE — verifica si ganó
            if (estaResuelto(tablero)) {
                juegoGanado = true
            }
        }
    }

    // PIPE — diálogo de victoria con meta mínima
    if (juegoGanado) {
        VictoriaDialog(
            movimientosRealizados = movimientos,
            metaMinima = metaMinima,
            onJugarDeNuevo = {
                val nuevo = reiniciarTablero()
                tablero = nuevo
                tableroInicial = nuevo
                movimientos = 0
                juegoGanado = false
            },
            onCerrar = {
                val nuevo = reiniciarTablero()
                tablero = nuevo
                tableroInicial = nuevo
                movimientos = 0
                juegoGanado = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Puzzle Deslizante",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A237E)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // NICOLAS — contador UI
        ContadorUI(movimientos)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Meta mínima: $metaMinima movimientos",
            fontSize = 14.sp,
            color = Color(0xFF888888)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // YARITZA — tablero visual
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            for (fila in 0..2) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    for (columna in 0..2) {
                        val indice = fila * 3 + columna
                        val numero = tablero[indice]
                        val esVacia = numero == 0

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (esVacia) Color(0xFFF0F0F0)
                                    else Color(0xFF3F51B5)
                                )
                                .clickable(enabled = !esVacia) {
                                    moverPieza(indice)
                                }
                        ) {
                            if (!esVacia) {
                                Text(
                                    text = numero.toString(),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // NICOLAS — botón reiniciar
        BotonesUI(onReiniciar = {
            val nuevo = reiniciarTablero()
            tablero = nuevo
            tableroInicial = nuevo
            movimientos = 0
            juegoGanado = false
        })
    }
}