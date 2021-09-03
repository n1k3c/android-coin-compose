package com.nikec.coincompose.coins.ui.details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.model.SparklineIn7d
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
fun CoinContent(coin: Coin?) {
    Column(modifier = Modifier.fillMaxSize()) {
        Sparkline(
            sparklineIn7d = coin?.sparkline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Sparkline(
    @PreviewParameter(FakeSparklineProvider::class) sparklineIn7d: SparklineIn7d?
) {
    if (sparklineIn7d == null) return

    val prices = sparklineIn7d.price.toMutableList()
    val maxPrice = prices.maxOrNull()
    val minPrice = prices.minOrNull()

    prices.remove(maxPrice)
    prices.remove(minPrice)

    if (maxPrice == null || minPrice == null) return

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

        val dotColor = Color.Black
        val dotRadius = 10f

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

        val minPriceDaysXAxis = xAxisOffset
        val minPriceDaysYAxis = canvasHeight - yAxisOffset

        // min price/days value
        drawCircle(
            color = Color.Red,
            radius = dotRadius,
            center = Offset(minPriceDaysXAxis, minPriceDaysYAxis)
        )

        val maxPriceXAxis = xAxisOffset
        val maxPriceYAxis = 0f

        // max price value
        drawCircle(
            color = Color.Red,
            radius = dotRadius,
            center = Offset(maxPriceXAxis, maxPriceYAxis)
        )

        // max days value
        val maxDaysXAxis = canvasWidth
        val maxDaysYAxis = yAxisOffset

        drawCircle(
            color = Color.Red,
            radius = dotRadius,
            center = Offset(maxDaysXAxis, canvasHeight - maxDaysYAxis)
        )

        // total distance (x and y axis)
        val totalDistanceXAxis = LineChartHelper.calculateDistance(
            startX = minPriceDaysXAxis,
            startY = minPriceDaysYAxis,
            endX = maxDaysXAxis,
            endY = maxDaysYAxis
        )

        val totalDistanceYAxis = LineChartHelper.calculateDistance(
            startX = minPriceDaysXAxis,
            startY = minPriceDaysYAxis,
            endX = maxPriceXAxis,
            endY = maxPriceYAxis
        )

        val maxMinPriceDiff = maxPrice - minPrice

        val spaceBetweenDotsXAxis = totalDistanceXAxis.roundToInt() / prices.size

        prices.forEachIndexed { index, price ->
            val maxPriceDiffPercentage = (maxPrice - price) / maxMinPriceDiff

            val y = ((totalDistanceYAxis * (maxPriceDiffPercentage))).toFloat()
            val x = (index * spaceBetweenDotsXAxis) + xAxisOffset

            drawCircle(
                color = dotColor,
                radius = dotRadius,
                center = Offset(x, y)
            )
        }

//        val textPaint = Paint().apply {
//            textSize = 20f
//            textAlign = Paint.Align.CENTER
//            color = 0xFF000000.toInt()
//            isAntiAlias = true
//        }
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
