package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.Vies.createLineChartView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val relLayoutChest: RelativeLayout = findViewById(R.id.relLayoutChest)
        val relLayoutBiceps: RelativeLayout = findViewById(R.id.relLayoutBiceps)
        val relLayoutBack: RelativeLayout = findViewById(R.id.relLayoutBack)
        val relLayoutTriceps: RelativeLayout = findViewById(R.id.relLayoutTriceps)
        val relLayoutLeg: RelativeLayout = findViewById(R.id.relLayoutLeg)
        val relLayoutShoulder: RelativeLayout = findViewById(R.id.relLayoutShoulder)
        val parentLayout = findViewById<LinearLayout>(R.id.chartContainer)
        val clickListener = View.OnClickListener { view ->
            val bodyPart = when (view.id) {
                R.id.relLayoutChest -> "Chest"
                R.id.relLayoutBiceps -> "Biceps"
                R.id.relLayoutBack -> "Back"
                R.id.relLayoutTriceps -> "Triceps"
                R.id.relLayoutLeg -> "Leg"
                R.id.relLayoutShoulder -> "Shoulder"
                else -> ""
            }
            navigateToMyActivity(bodyPart)
        }

        relLayoutChest.setOnClickListener(clickListener)
        relLayoutBiceps.setOnClickListener(clickListener)
        relLayoutBack.setOnClickListener(clickListener)
        relLayoutTriceps.setOnClickListener(clickListener)
        relLayoutLeg.setOnClickListener(clickListener)
        relLayoutShoulder.setOnClickListener(clickListener)
        val dataPoints = listOf(
            Pair("Mon", 30f),
            Pair("Tue", 50f),
            Pair("Wed", 45f),
            Pair("Thu", 60f),
            Pair("Fri", 55f),
            Pair("Sat", 70f),
            Pair("Sun", 65f)
        )

        // Create and add the LineChartView to the chartContainer
        createLineChartView(
            context = this,
            parent = parentLayout,
            dataPoints = dataPoints,
            maxDataPointY = 100f,
            xAxisColor = Color.BLACK,
            yAxisColor = Color.BLACK,
            gridColor = Color.LTGRAY,
            barColor = ContextCompat.getColor(this, R.color.teal),
            yAxisSteps = 5  // Number of steps for Y-axis labels
        )
    }

    private fun navigateToMyActivity(bodyPart: String) {
        val intent = Intent(this, BodyPartActivity::class.java).apply {
            putExtra("BODY_PART", bodyPart)
        }
        startActivity(intent)
    }
}

