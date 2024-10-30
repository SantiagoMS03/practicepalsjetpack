// BarChart.kt
package com.zybooks.practicepals.ui.barchart

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

@Composable
fun BarChart(
    data: Map<String, Long>,
    average: Long, // New parameter for average practice time
    modifier: Modifier = Modifier,
    barColor: Color = Color(0xFF6200EE),
    averageLineColor: Color = Color.Red, // Color for the average line
    labelColor: Color = Color.Black,
    maxBarHeight: Float = 200f,
    yAxisLabelCount: Int = 5
) {
    // Determine the maximum value to scale the bars
    val maxValue = max(data.values.maxOrNull()?.toFloat() ?: 1f, average.toFloat())

    val scaledData = data.mapValues { it.value.toFloat() }

    // Calculate Y-axis labels
    val step = max(1f, maxValue / yAxisLabelCount)

    // Animation for bars
    val animatedValues = remember { mutableStateMapOf<String, Float>() }

    scaledData.forEach { (day, value) ->
        val targetValue = (value / maxValue) * maxBarHeight
        val animatedValue by animateFloatAsState(
            targetValue = targetValue,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
        animatedValues[day] = animatedValue
    }

    // Calculate the Y-position for the average line
    val averageY = (average.toFloat() / maxValue) * maxBarHeight

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp, start = 32.dp), // Added start padding for Y-axis labels
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            scaledData.forEach { (day, value) ->
                val barHeight = animatedValues[day] ?: 0f

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Bar
                    Canvas(
                        modifier = Modifier
                            .height(barHeight.dp)
                            .width(24.dp)
                    ) {
                        drawRect(
                            color = barColor,
                            size = size
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Day Label
                    Text(
                        text = day,
                        fontSize = 12.sp,
                        color = labelColor
                    )

                    // Practice Time Label
                    Text(
                        text = "${(barHeight / maxBarHeight * maxValue).toInt()}m",
                        fontSize = 12.sp,
                        color = labelColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Y-Axis Labels
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in yAxisLabelCount downTo 0) {
                val label = (i * step).toInt()
                Text(
                    text = "$label",
                    fontSize = 12.sp,
                    color = labelColor
                )
            }
        }

        // Y-axis line
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.Gray,
                start = androidx.compose.ui.geometry.Offset(x = 32.dp.toPx(), y = 0f),
                end = androidx.compose.ui.geometry.Offset(x = 32.dp.toPx(), y = size.height),
                strokeWidth = 2f
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = averageLineColor,
                start = androidx.compose.ui.geometry.Offset(x = 32.dp.toPx(), y = maxBarHeight.dp.toPx() - averageY),
                end = androidx.compose.ui.geometry.Offset(x = size.width, y = maxBarHeight.dp.toPx() - averageY),
                strokeWidth = 2f,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) // Dotted pattern
            )
        }

        // Average Label
        Text(
            text = "7-Day Avg: ${average}m",
            fontSize = 12.sp,
            color = averageLineColor,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 8.dp)
        )
    }
}
