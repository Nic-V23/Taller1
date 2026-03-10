package com.example.taller1

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BotonesUI(onReiniciar: () -> Unit) {
    Button(
        onClick = onReiniciar,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1A237E)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .padding(top = 32.dp)
    ) {
        Text(
            text = "Reiniciar",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}