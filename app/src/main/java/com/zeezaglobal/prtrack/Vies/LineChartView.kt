package com.zeezaglobal.prtrack.Vies

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.max
import kotlin.math.min

 class LineChartView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

     // Mutable properties with default values
     var dataPoints: List<Pair<String, Float>> = listOf(
         Pair("Mon", 30f),
         Pair("Tue", 50f),
         Pair("Wed", 45f),
         Pair("Thu", 60f),
         Pair("Fri", 55f),
         Pair("Sat", 70f),
         Pair("Sun", 65f)
     )
     var yLabels: List<String> = listOf("0kg", "20kg", "40kg", "60kg", "80kg")
     var maxDataPointY: Float = 80f

     // Paint objects with default styles
     val paint = Paint().apply {
         color = Color.BLUE
         strokeWidth = 5f
         style = Paint.Style.STROKE
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
         pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
     }

     override fun onDraw(canvas: Canvas) {
         super.onDraw(canvas)

         val padding = 100f
         val originX = padding
         val originY = height - padding
         val chartWidth = width - 2 * padding
         val chartHeight = height - 2 * padding

         // Draw X and Y axes
         canvas.drawLine(originX, originY, originX + chartWidth, originY, axisPaint)
         canvas.drawLine(originX, originY, originX, originY - chartHeight, axisPaint)

         // Draw the grid
         // Horizontal lines
         yLabels.forEachIndexed { index, _ ->
             val y = originY - index / (yLabels.size - 1).toFloat() * chartHeight
             canvas.drawLine(originX, y, originX + chartWidth, y, gridPaint)
         }

         // Vertical lines
         dataPoints.forEachIndexed { index, _ ->
             val x = originX + index / (dataPoints.size - 1).toFloat() * chartWidth
             canvas.drawLine(x, originY, x, originY - chartHeight, gridPaint)
         }

         // Draw the line chart
         for (i in 0 until dataPoints.size - 1) {
             val startX = originX + i / (dataPoints.size - 1).toFloat() * chartWidth
             val startY = originY - (dataPoints[i].second / maxDataPointY) * chartHeight
             val stopX = originX + (i + 1) / (dataPoints.size - 1).toFloat() * chartWidth
             val stopY = originY - (dataPoints[i + 1].second / maxDataPointY) * chartHeight
             canvas.drawLine(startX, startY, stopX, stopY, paint)
         }

         // Draw X axis labels
         dataPoints.forEachIndexed { index, pair ->
             val x = originX + index / (dataPoints.size - 1).toFloat() * chartWidth
             canvas.drawText(pair.first, x - 30f, originY + 40f, textPaint)
         }

         // Draw Y axis labels
         yLabels.forEachIndexed { index, label ->
             val y = originY - index / (yLabels.size - 1).toFloat() * chartHeight
             canvas.drawText(label, originX - 70f, y + 10f, textPaint)
         }
     }
 }