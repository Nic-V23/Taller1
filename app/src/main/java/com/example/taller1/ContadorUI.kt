package com.example.taller1

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContadorUI(movimientos: Int) {
    Text(
        text = "Movimientos: $movimientos",
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF37474F),
        modifier = Modifier.padding(bottom = 16.dp)
    )
}