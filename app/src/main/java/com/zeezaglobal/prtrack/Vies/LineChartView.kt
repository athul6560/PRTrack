package com.zeezaglobal.prtrack.Vies

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.zeezaglobal.prtrack.R
import kotlin.math.max
import kotlin.math.min

class LineChartView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var dataPoints: List<Pair<String, Float>> = listOf(
        Pair("Mon", 30f),
        Pair("Tue", 50f),
        Pair("Wed", 45f),
        Pair("Thu", 60f),
        Pair("Fri", 55f),
        Pair("Sat", 70f),
        Pair("Sun", 65f)
    )
    var yLabels: List<String> = listOf("5kg", "10kg", "15kg", "20kg")
    var maxDataPointY: Float = 80f

    val barPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }

    val axisPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 3f
    }

    val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 30f
    }

    val gridPaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    val dottedLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 3f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(10f, 20f), 0f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val padding = 100f
        val originX = padding
        val originY = height - padding
        val chartWidth = width - 2 * padding
        val chartHeight = height - 2 * padding

        val barWidth = (chartWidth / dataPoints.size) - 20f
        val cornerRadius = 20f

        // Draw X and Y axes
        canvas.drawLine(originX, originY, originX + chartWidth, originY, axisPaint)
        canvas.drawLine(originX, originY, originX, originY - chartHeight, axisPaint)

        // Draw the grid
        yLabels.forEachIndexed { index, _ ->
            val y = originY - index / (yLabels.size - 1).toFloat() * chartHeight
            canvas.drawLine(originX, y, originX + chartWidth, y, gridPaint)
        }

        // Draw the bars with rounded corners
        dataPoints.forEachIndexed { index, pair ->
            // Change the bar color to grey for all bars except the last one
            if (index == dataPoints.size - 1) {
                barPaint.color = context.getColor(R.color.teal) // Color for the last bar
            } else {
                barPaint.color = Color.GRAY // Color for the other bars
            }

            val barLeft = originX + index * (barWidth + 20f)
            val barRight = barLeft + barWidth
            val barTop = originY - (pair.second / maxDataPointY) * chartHeight
            canvas.drawRoundRect(barLeft, barTop, barRight, originY, cornerRadius, cornerRadius, barPaint)
        }

        // Draw X axis labels
        dataPoints.forEachIndexed { index, pair ->
            val x = originX + index * (barWidth + 20f) + barWidth / 2
            canvas.drawText(pair.first, x - 30f, originY + 40f, textPaint)
        }

        // Draw Y axis labels
        yLabels.forEachIndexed { index, label ->
            val y = originY - index / (yLabels.size - 1).toFloat() * chartHeight
            canvas.drawText(label, originX - 70f, y + 10f, textPaint)
        }

        // Draw the dotted red line from the Y-axis to the last data point
        if (dataPoints.isNotEmpty()) {
            val lastDataPoint = dataPoints.last()
            val lastBarTop = originY - (lastDataPoint.second / maxDataPointY) * chartHeight
            canvas.drawLine(originX, lastBarTop, originX + chartWidth, lastBarTop, dottedLinePaint)

            // Draw the last data point value on top of the last bar
            val lastBarLeft = originX + (dataPoints.size - 1) * (barWidth + 20f)
            val lastBarRight = lastBarLeft + barWidth
            val labelX = (lastBarLeft + lastBarRight) / 2
            canvas.drawText(
                "${lastDataPoint.second}kg",
                labelX - 30f,
                lastBarTop - 10f,  // Adjust position to be slightly above the bar
                textPaint
            )
        }
    }}

    fun generateYLabels(maxValue: Float, steps: Int): List<String> {
        val interval = maxValue / steps
        return (0..steps).map { "${(it * interval).toInt()}kg" }
    }

fun createLineChartView(
    context: Context,
    parent: ViewGroup,
    dataPoints: List<Pair<String, Float>>,
    maxDataPointY: Float,
    xAxisColor: Int = Color.BLACK,
    yAxisColor: Int = Color.BLACK,
    gridColor: Int = Color.LTGRAY,
    barColor: Int = Color.BLUE,
    yAxisSteps: Int = 4
): LineChartView {
    val yLabels = generateYLabels(maxDataPointY, yAxisSteps)

    val barChartView = LineChartView(context).apply {
        this.dataPoints = dataPoints
        this.yLabels = yLabels
        this.maxDataPointY = maxDataPointY
        this.axisPaint.color = xAxisColor
        this.gridPaint.color = gridColor
        this.barPaint.color = barColor
    }

    val layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    barChartView.layoutParams = layoutParams
    parent.addView(barChartView)

    return barChartView
}