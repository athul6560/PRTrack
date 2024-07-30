package com.zeezaglobal.prtrack

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeezaglobal.prtrack.Vies.createLineChartView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val parentLayout = findViewById<LinearLayout>(R.id.chartContainer)

        createLineChartView(
            context = this,
            parent = parentLayout,
            dataPoints = listOf(
                Pair("Jan", 10f),
                Pair("Feb", 20f),
                Pair("Mar", 30f),
                Pair("Apr", 40f),
                Pair("May", 50f)
            ),
            yLabels = listOf("0kg", "20kg", "40kg", "60kg"),
            xAxisColor = Color.RED,
            yAxisColor = Color.GREEN,
            gridColor = Color.GRAY,
            lineColor = Color.MAGENTA,
            maxDataPointY = 60f
        )
    }
    }

