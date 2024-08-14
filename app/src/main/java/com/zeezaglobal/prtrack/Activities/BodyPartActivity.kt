package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.RRAdapters.WorkoutLogAdapter
import com.zeezaglobal.prtrack.Utils.getSampleWorkoutLogs

import com.zeezaglobal.prtrack.ViewModels.LogsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
        viewModel = ViewModelProvider(this).get(LogsViewModel::class.java)
        // Retrieve the body part information from the intent
        val bodyPart = intent.getStringExtra("BODY_PART")

        val recyclerView: RecyclerView = findViewById(R.id.recycle_review)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = WorkoutLogAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.workoutLogs
                    .onEach { workoutLogs ->
                        // Update the adapter with the new data
                        adapter.updateData(workoutLogs)
                    }
                    .launchIn(this) // Launch the flow collection in the current coroutine scope
            }
        }

        // Fetch the workout logs
        viewModel.getWorkoutLogs(1)


        bodyPartTextView.text = bodyPart+" Workouts"


      
        button.setOnClickListener {
            val intent = Intent(this, AddWorkoutActivity::class.java).apply {
                putExtra("BODY_PART", bodyPart)
            }
            startActivity(intent)
            }

        }
    private fun getWorkoutIdFromBodyPart(bodyPart: String?): Int {
        // Implement the logic to convert bodyPart to workoutId
        // This could be a lookup from a predefined list or another data source
        return 1 // Placeholder value
    }
    }