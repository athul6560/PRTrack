package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
        createLineChartView(
            context = this,
            parent = parentLayout,
            dataPoints = listOf(
                Pair("Mon", 10f),
                Pair("Tue", 80f),
                Pair("Wed", 30f)

            ),
            yLabels = listOf("0kg", "20kg", "40kg", "60kg"),
            xAxisColor = Color.BLACK,
            yAxisColor = Color.GREEN,
            gridColor = Color.GRAY,
            lineColor = Color.BLUE,
            maxDataPointY = 60f
        )
    }

    private fun navigateToMyActivity(bodyPart: String) {
        val intent = Intent(this, BodyPartActivity::class.java).apply {
            putExtra("BODY_PART", bodyPart)
        }
        startActivity(intent)
    }
}

