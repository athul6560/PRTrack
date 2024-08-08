package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.Vies.createLineChartView
import com.zeezaglobal.prtrack.ViewModels.LogsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BodyPartActivity : AppCompatActivity() {

    private lateinit var viewModel: LogsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_body_part)
        val bodyPartTextView: TextView = findViewById(R.id.heading)
        val button: TextView = findViewById(R.id.button)
        val parentLayout = findViewById<LinearLayout>(R.id.chartContainer)
        // Retrieve the body part information from the intent
        val bodyPart = intent.getStringExtra("BODY_PART")



        viewModel = ViewModelProvider(this).get(LogsViewModel::class.java)


        // Display the body part information in the TextView
        bodyPartTextView.text = bodyPart ?: "No body part information available"
        val workoutId = 1L

        viewModel.getWorkoutLogs(workoutId).observe(this) { workoutLogs ->
            val dataPoints = workoutLogs.map {
                val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(Date(it.date))
                Pair(dayOfWeek, it.weight.toFloat())
            }

            createLineChartView(
                context = this,
                parent = parentLayout,
                dataPoints = dataPoints,
                maxDataPointY = 100f,
                xAxisColor = Color.BLACK,
                yAxisColor = Color.BLACK,
                gridColor = Color.LTGRAY,
                barColor = ContextCompat.getColor(this, R.color.teal),
                yAxisSteps = 5
            )
        }
        button.setOnClickListener {
                val intent = Intent(this, AddExerciseActivity::class.java)
                startActivity(intent)
            }

        }
    }