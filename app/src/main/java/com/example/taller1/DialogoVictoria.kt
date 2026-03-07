package com.example.taller1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


private fun colorSegunEficiencia(realizados: Int, meta: Int): Color {
    val diferencia = realizados - meta
    return when {
        diferencia == 0    -> Color(0xFF2E7D32)   // Verde oscuro
        diferencia in 1..5 -> Color(0xFFF9A825)   // Amarillo ámbar
        else               -> Color(0xFFC62828)   // Rojo oscuro
    }
}


@Composable
fun VictoriaDialog(
    movimientosRealizados: Int,
    metaMinima: Int,
    onJugarDeNuevo: () -> Unit,
    onCerrar: () -> Unit
) {
    val mensaje = evaluarEficiencia(movimientosRealizados, metaMinima)
    val colorMensaje = colorSegunEficiencia(movimientosRealizados, metaMinima)

    Dialog(onDismissRequest = onCerrar) {
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(28.dp)
            ) {


                Text(
                    text = "🎉 ¡Puzzle Resuelto!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))


                EstadisticaFila(
                    etiqueta = "Tus movimientos",
                    valor = movimientosRealizados.toString(),
                    colorValor = Color(0xFF37474F)
                )

                Spacer(modifier = Modifier.height(8.dp))

                EstadisticaFila(
                    etiqueta = "Meta mínima (BFS)",
                    valor = if (metaMinima >= 0) metaMinima.toString() else "N/A",
                    colorValor = Color(0xFF37474F)
                )

                Spacer(modifier = Modifier.height(20.dp))


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorMensaje.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 14.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = mensaje,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorMensaje,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))


                Button(
                    onClick = onJugarDeNuevo,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A237E)
                    )
                ) {
                    Text(
                        text = "Jugar de nuevo",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    onClick = onCerrar,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cerrar",
                        color = Color(0xFF757575),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
private fun EstadisticaFila(
    etiqueta: String,
    valor: String,
    colorValor: Color
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = etiqueta,
            fontSize = 14.sp,
            color = Color(0xFF616161)
        )
        Text(
            text = valor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorValor
        )
    }
}