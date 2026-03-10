package com.example.taller1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

private val ColorFondo      = Color(0xFF1A1A2E)
private val ColorTablero    = Color(0xFF16213E)
private val ColorCelda      = Color(0xFF0F3460)
private val ColorCeldaVacia = Color(0xFF0A1628)
private val ColorTexto      = Color(0xFFE0E0E0)
private val ColorAcento     = Color(0xFFE94560)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaPrincipal()
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {
    var casillas    by remember { mutableStateOf(reiniciarTablero()) }
    var movimientos by remember { mutableIntStateOf(0) }
    val juegoGanado = casillas == TABLERO_RESUELTO

    fun moverPieza(index: Int) {
        if (juegoGanado) return
        val posVacia  = casillas.indexOf(0)
        val fila      = index / 3
        val columna   = index % 3
        val filaV     = posVacia / 3
        val columnaV  = posVacia % 3

        val esAdyacente = (fila == filaV && abs(columna - columnaV) == 1) ||
                (columna == columnaV && abs(fila - filaV) == 1)

        if (esAdyacente) {
            val nuevas = casillas.toMutableList()
            nuevas[posVacia] = nuevas[index]
            nuevas[index]    = 0
            casillas    = nuevas
            movimientos++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorFondo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Puzzle Deslizante",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = ColorAcento,
            modifier = Modifier.padding(top = 16.dp)
        )

        ContadorMovimientos(movimientos)

        TableroJuego(
            casillas      = casillas,
            juegoGanado   = juegoGanado,
            onCeldaTocada = { index -> moverPieza(index) }
        )

        if (juegoGanado) {
            Text(
                text = "¡Felicidades! 🎉\nResuelto en $movimientos movimientos",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4CAF50),
                textAlign = TextAlign.Center
            )
        } else {
            Spacer(modifier = Modifier.height(40.dp))
        }

        Button(
            onClick = {
                casillas    = reiniciarTablero()
                movimientos = 0
            },
            colors  = ButtonDefaults.buttonColors(containerColor = ColorAcento),
            shape   = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "Reiniciar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun ContadorMovimientos(movimientos: Int) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF0F3460),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = "Movimientos: $movimientos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = ColorTexto,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )
    }
}

@Composable
fun TableroJuego(
    casillas: List<Int>,
    juegoGanado: Boolean,
    onCeldaTocada: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(ColorTablero)
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for (fila in 0..2) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (columna in 0..2) {
                        val index = fila * 3 + columna
                        CeldaPuzzle(
                            numero      = casillas[index],
                            juegoGanado = juegoGanado,
                            onClick     = { onCeldaTocada(index) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CeldaPuzzle(
    numero: Int,
    juegoGanado: Boolean,
    onClick: () -> Unit
) {
    val estaVacia  = numero == 0
    val colorFondo = if (estaVacia) ColorCeldaVacia else ColorCelda

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(96.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorFondo)
            .then(
                if (!estaVacia && !juegoGanado)
                    Modifier.clickable { onClick() }
                else
                    Modifier
            )
    ) {
        if (!estaVacia) {
            Text(
                text       = numero.toString(),
                fontSize   = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = ColorTexto,
                textAlign  = TextAlign.Center
            )
        }
    }
}


