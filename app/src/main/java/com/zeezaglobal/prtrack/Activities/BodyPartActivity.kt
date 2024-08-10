package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.RRAdapters.WorkoutLogAdapter
import com.zeezaglobal.prtrack.Utils.getSampleWorkoutLogs
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
     
        // Retrieve the body part information from the intent
        val bodyPart = intent.getStringExtra("BODY_PART")

        val recyclerView: RecyclerView = findViewById(R.id.recycle_review)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val workoutLogs=getSampleWorkoutLogs()
        val adapter = WorkoutLogAdapter(workoutLogs, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(LogsViewModel::class.java)


     
        bodyPartTextView.text = bodyPart ?: "No body part information available"
        val workoutId = 1

      
        button.setOnClickListener {
            val intent = Intent(this, AddWorkoutActivity::class.java).apply {
                putExtra("BODY_PART", bodyPart)
            }
            startActivity(intent)
            }

        }
    }