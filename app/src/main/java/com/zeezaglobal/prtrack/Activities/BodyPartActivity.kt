package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.RRAdapters.WorkoutLogAdapter
import com.zeezaglobal.prtrack.RRAdapters.WorkoutPagerAdapter
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import com.zeezaglobal.prtrack.Utils.getSampleWorkoutLogs

import com.zeezaglobal.prtrack.ViewModels.LogsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BodyPartActivity : AppCompatActivity() {

    private lateinit var viewModel: LogsViewModel
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = (application as MyApp).database
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


        bodyPartTextView.text = bodyPart + " Workouts"



        button.setOnClickListener {
            showAddWorkoutDialog(bodyPart)

        }
    }

    private fun showAddWorkoutDialog(bodyPart: String?) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_workout, null)
        dialogBuilder.setView(dialogView)

        val viewPager: ViewPager2 = dialogView.findViewById(R.id.viewPager)
        val tabLayout: TabLayout = dialogView.findViewById(R.id.tabLayout)

// Set up the ViewPager2 adapter
        viewPager.adapter = WorkoutPagerAdapter(this)

// Set up the TabLayoutMediator to connect the TabLayout with the ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // You don't need to set anything specific here for dots indicator
        }.attach()


        val alertDialog = dialogBuilder.create()
        val pagerAdapter = viewPager.adapter as WorkoutPagerAdapter

        val page1View =
            (pagerAdapter as WorkoutPagerAdapter).createViewHolder(viewPager, 0).itemView
        val editTextWorkoutName: EditText = page1View.findViewById(R.id.edit_text_workout_name)

        val page2View =
            (pagerAdapter as WorkoutPagerAdapter).createViewHolder(viewPager, 1).itemView
        val numberPickerMain: NumberPicker = page2View.findViewById(R.id.numberPicker_main)
        val numberPickerDecimal: NumberPicker = page2View.findViewById(R.id.numberPicker_decimal)
        val submitButton: Button = dialogView.findViewById(R.id.submit_button)
        numberPickerMain.maxValue = 50
        numberPickerMain.minValue = 1
        alertDialog.show()
        submitButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {

                    val bodyPartName = intent.getStringExtra("BODY_PART")
                    val bodyPartId = getBodyPartIdUsingIntent(bodyPartName)

                    if (bodyPartId != null) {
                        val exercise = Workout(
                            workoutName = "athul",
                            bodyPartId = bodyPartId
                        )

                        database.workoutDao().insertWorkout(exercise)
                    } else {

                    }
                }
            }
        }


    }

    private fun getWorkoutIdFromBodyPart(bodyPart: String?): Int {
        // Implement the logic to convert bodyPart to workoutId
        // This could be a lookup from a predefined list or another data source
        return 1 // Placeholder value
    }

    private suspend fun getBodyPartIdUsingIntent(bodyPartName: String?): Int? {
        return bodyPartName?.let { name ->
            withContext(Dispatchers.IO) {
                database.bodyPartDao().getBodyPartIdByName(name)
            }
        }
    }
}