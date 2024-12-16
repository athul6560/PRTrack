package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.Views.RectangleView

import com.zeezaglobal.prtrack.Views.createLineChartView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val relLayoutChest: RelativeLayout = findViewById(R.id.relLayoutChest)
        val relLayoutBiceps: RelativeLayout = findViewById(R.id.relLayoutBiceps)
        val relLayoutCore: RelativeLayout = findViewById(R.id.relLayoutCore)
        val relLayoutLegs: RelativeLayout = findViewById(R.id.relLayoutLegs)
        val relLayoutTriceps: RelativeLayout = findViewById(R.id.relLayoutTriceps)
        val relLayoutShoulder: RelativeLayout = findViewById(R.id.relLayoutShoulder)
        val relLayoutBack: RelativeLayout = findViewById(R.id.relLayoutBack)


        val clickListener = View.OnClickListener { view ->
            val bodyPart = when (view.id) {
                R.id.relLayoutChest -> "Chest"
                R.id.relLayoutBiceps -> "Biceps"
                R.id.relLayoutLegs -> "Legs"
                R.id.relLayoutTriceps -> "Triceps"
                R.id.relLayoutShoulder -> "Shoulder"
                R.id.relLayoutCore -> "Core"
                R.id.relLayoutBack -> "Back"

                else -> ""
            }
            navigateToMyActivity(bodyPart)
        }

        relLayoutChest.setOnClickListener(clickListener)
        relLayoutBiceps.setOnClickListener(clickListener)
        relLayoutLegs.setOnClickListener(clickListener)
        relLayoutTriceps.setOnClickListener(clickListener)
        relLayoutShoulder.setOnClickListener(clickListener)
        relLayoutCore.setOnClickListener(clickListener)
        relLayoutBack.setOnClickListener(clickListener)


        val contributionGrid = findViewById<GridLayout>(R.id.contributionGrid)

        val (rectWidth, rectHeight, margin) = calculateRectangleSizeAndMargin()

        // Generate mock activity data for 31 days
        val activityData = generateMockActivityData()

        // Add rectangles to the grid with gaps
        for (activity in activityData) {
            val rectangle = RectangleView(this)
            rectangle.activityLevel = activity
            rectangle.setRectangleSize(rectWidth, rectHeight, margin)
            contributionGrid.addView(rectangle)
        }


    }
    private fun calculateRectangleSizeAndMargin(): Triple<Int, Int, Int> {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val padding = 16 * 2 // Padding on both sides
        val columns = 7 // Number of columns (7 days per week)

        // Define the gap between elements (in pixels)
        val margin = 8

        // Subtract total gap space from available screen width
        val totalGapSpace = (columns - 1) * margin
        val rectWidth = (screenWidth - padding - totalGapSpace) / columns
        val rectHeight = rectWidth / 2 // Rectangle aspect ratio
        return Triple(rectWidth, rectHeight, margin)
    }

    private fun generateMockActivityData(): List<Int> {
        return List(31) { (0..4).random() }
    }
    private fun navigateToMyActivity(bodyPart: String) {
        val intent = Intent(this, BodyPartActivity::class.java).apply {
            putExtra("BODY_PART", bodyPart)
        }
        startActivity(intent)
    }
}

