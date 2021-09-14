package com.nikec.coincompose.coins.ui.details

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.core.model.SparklineIn7d
import com.nikec.coincompose.core.extensions.formatToString
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

@Preview(showBackground = true)
@Composable
fun Sparkline(
    modifier: Modifier = Modifier,
    @PreviewParameter(FakeSparklineProvider::class) sparklineIn7d: SparklineIn7d?
) {
    if (sparklineIn7d == null) return

    val prices = sparklineIn7d.price
    val maxPrice = prices.maxOrNull()
    val minPrice = prices.minOrNull()


    if (maxPrice == null || minPrice == null) return

    val gridYAxisName = stringResource(id = R.string.seven_days_long)

    Canvas(
        modifier = Modifier.fillMaxWidth() then modifier
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val yAxisOffset = 0f
        val xAxisOffset = 0f

        val axisStrokeWidth = 6f
        val axisStrokeColor = Color(0xFFF7F7F7)
        val axisNumberOfLines = 5

//        val dotColor = Color.Black
//        val dotRadius = 7f

        val minPriceDaysYAxis = canvasHeight - yAxisOffset

        val maxPriceYAxis = 0f
        val maxDaysYAxis = canvasHeight - yAxisOffset

        val gridValueXOffset = 6f
        val gridValueYOffset = 20f
        val gridValueYEndOffset = 3f
        val gridValueTextSize = 20f

        val minPriceColor = 0xFFBE2525.toInt()
        val maxPriceColor = 0xFF51C76A.toInt()

        val totalDistanceXAxis = LineChartHelper.calculateDistance(
            startX = xAxisOffset,
            startY = minPriceDaysYAxis,
            endX = canvasWidth,
            endY = maxDaysYAxis
        )

        val totalDistanceYAxis = LineChartHelper.calculateDistance(
            startX = xAxisOffset,
            startY = minPriceDaysYAxis,
            endX = xAxisOffset,
            endY = maxPriceYAxis
        )

        val maxMinPriceDiff = maxPrice - minPrice

        val spaceBetweenDotsXAxis = totalDistanceXAxis / (prices.size - 1)

        val points = prices.mapIndexed { index, price ->
            val maxPriceDiffPercentage = (maxPrice - price) / maxMinPriceDiff

            val y = ((totalDistanceYAxis * (maxPriceDiffPercentage))).toFloat()
            val x = (index * spaceBetweenDotsXAxis) + xAxisOffset

            Offset(x, y)
        }

        // grid
        val gridYSpace = totalDistanceXAxis / axisNumberOfLines
        val gridXSpace = totalDistanceYAxis / axisNumberOfLines

        for (i in 0..axisNumberOfLines) {
            // y axis
            drawLine(
                color = axisStrokeColor,
                start = Offset((i * gridYSpace) + xAxisOffset, 0f),
                end = Offset((i * gridYSpace) + xAxisOffset, canvasHeight),
                strokeWidth = axisStrokeWidth
            )

            // x axis
            drawLine(
                color = axisStrokeColor,
                start = Offset(0f, (i * gridXSpace) - yAxisOffset),
                end = Offset(canvasWidth, (i * gridXSpace) - yAxisOffset),
                strokeWidth = axisStrokeWidth
            )
        }

        // points and lines
        points.forEachIndexed { index, offset ->
//            drawCircle(
//                color = dotColor,
//                radius = dotRadius,
//                center = offset
//            )

            if (index == 0) return@forEachIndexed

            val pointBefore = points[index - 1]

            drawLine(
                color = Color.Black,
                start = pointBefore,
                end = offset,
                strokeWidth = 7f
            )
        }

        // grid values
        val gridTextValuePaint = Paint().apply {
            textSize = gridValueTextSize
            color = maxPriceColor
            isAntiAlias = true
        }

        drawContext.canvas.nativeCanvas.drawText(
            "$" + maxPrice.formatToString(),
            gridValueXOffset,
            gridValueYOffset,
            gridTextValuePaint
        )

        gridTextValuePaint.color = minPriceColor

        drawContext.canvas.nativeCanvas.drawText(
            "$" + minPrice.formatToString(),
            gridValueXOffset,
            canvasHeight - gridValueYEndOffset,
            gridTextValuePaint
        )

        gridTextValuePaint.apply {
            color = 0xFF000000.toInt()
            textAlign = Paint.Align.RIGHT
        }

        drawContext.canvas.nativeCanvas.drawText(
            gridYAxisName,
            canvasWidth - gridValueXOffset,
            canvasHeight - gridValueYEndOffset,
            gridTextValuePaint
        )
    }
}

object LineChartHelper {

    fun calculateDistance(startX: Float, startY: Float, endX: Float, endY: Float): Float {
        return sqrt((startX - endX).pow(2) + (startY - endY).pow(2))
    }
}

class FakeSparklineProvider : PreviewParameterProvider<SparklineIn7d> {
    override val values: Sequence<SparklineIn7d> = sequenceOf(
        SparklineIn7d(
            price = listOf(
                48407.885932065044,
                48220.09639014465,
                47355.7647949307,
                47250.111254103365,
                47564.82143630801,
                47595.64282354106,
                47880.485505624325,
                47823.89017104759,
                48494.59325882068,
                48742.858467490885,
                48714.41853890469,
                48995.85465047788,
                48922.70542590135,
                48753.76857658705,
                48701.72190282694,
                48942.76918213867,
                48876.827163679605,
                48989.51208406698,
                48989.51208406698,
                49320.14321163297,
                48852.47841611081,
                48720.91695101708,
                47962.34759334175,
                47852.61135414922,
                46999.6507077132,
                46991.39034969912,
                47055.6892990653,
                47068.410382617316,
                46913.03804634985,
                47113.073643525844,
                46923.88439113699,
                47136.79287881744,
                47272.18225275718,
                46940.88146695009,
            )
        )
    )

    override val count: Int = values.count()
}
