package com.nikec.coincompose.coins.ui.details

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CoinContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Sparkline()
    }
}

@Preview(showBackground = true)
@Composable
fun Sparkline() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val yAxisOffset = 40f
        val xAxisOffset = 40f
        val axisStrokeWidth = 6f
        val axisStrokeColor = Color.Black

        // y axis
        drawLine(
            color = axisStrokeColor,
            start = Offset(xAxisOffset, 0f),
            end = Offset(xAxisOffset, canvasHeight),
            strokeWidth = axisStrokeWidth
        )

        // x axis
        drawLine(
            color = axisStrokeColor,
            start = Offset(0f, canvasHeight - yAxisOffset),
            end = Offset(canvasWidth, canvasHeight - yAxisOffset),
            strokeWidth = axisStrokeWidth
        )

        // min price value
        drawCircle(
            color = Color.Black,
            radius = 10f,
            center = Offset(xAxisOffset, canvasHeight - yAxisOffset)
        )

        // max price value
        drawCircle(
            color = Color.Black,
            radius = 10f,
            center = Offset(xAxisOffset, 0f)
        )

//        val textPaint = Paint().apply {
//            textSize = 20f
//            textAlign = Paint.Align.CENTER
//            color = 0xFF000000.toInt()
//            isAntiAlias = true
//        }
//
//
//        rotate(-90f, Offset(canvasWidth / 2, canvasHeight / 2)) {
//            drawContext.canvas.nativeCanvas.drawText(
//                "Price",
//                canvasWidth / 2,
//                canvasHeight / 2,
//                textPaint
//            )
//        }
    }
}
