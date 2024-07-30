package com.zeezaglobal.prtrack.Vies

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup

fun createLineChartView(
    context: Context,
    parent: ViewGroup,
    dataPoints: List<Pair<String, Float>>,
    yLabels: List<String>,
    xAxisColor: Int = Color.BLACK,
    yAxisColor: Int = Color.BLACK,
    gridColor: Int = Color.LTGRAY,
    lineColor: Int = Color.BLUE,
    maxDataPointY: Float = 80f
): LineChartView {
    val lineChartView = LineChartView(context).apply {
        this.dataPoints = dataPoints
        this.yLabels = yLabels
        this.maxDataPointY = maxDataPointY
        this.axisPaint.color = xAxisColor
        this.gridPaint.color = gridColor
        this.paint.color = lineColor
    }

    val layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    lineChartView.layoutParams = layoutParams
    parent.addView(lineChartView)

    return lineChartView
}