package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
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
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.RRAdapters.WorkoutLogAdapter
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import com.zeezaglobal.prtrack.Utils.getCurrentTimeInEpoch
import com.zeezaglobal.prtrack.Utils.getSampleWorkoutLogs
import com.zeezaglobal.prtrack.ViewModels.CombinedViewModel

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

    private lateinit var viewModel: CombinedViewModel
    private lateinit var database: AppDatabase
    private lateinit var workoutLogAdapter: WorkoutLogAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = (application as MyApp).database
        setContentView(R.layout.activity_body_part)
        val bodyPartTextView: TextView = findViewById(R.id.heading)
        val button: TextView = findViewById(R.id.button)
        viewModel = ViewModelProvider(this).get(CombinedViewModel::class.java)
        // Retrieve the body part information from the intent
        val bodyPart = intent.getStringExtra("BODY_PART")

        val recyclerView: RecyclerView = findViewById(R.id.recycle_review)
        recyclerView.layoutManager = LinearLayoutManager(this)
        workoutLogAdapter = WorkoutLogAdapter(
            emptyList(),
            context = this
        ) { weight ->
            // This code will run when the button in a RecyclerView item is clicked
            showAddWeightDialog(weight)
        }
        recyclerView.adapter = workoutLogAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.workoutsWithLogs
                    .onEach { workoutsWithLogs ->
                        workoutLogAdapter.updateData(workoutsWithLogs)
                    }
                    .launchIn(this)
            }
        }

        // Fetch the combined data
        lifecycleScope.launch {
            val bodyPartId = getBodyPartIdUsingIntent(bodyPart)
            bodyPartId?.let {
                viewModel.getWorkoutsWithLogsByBodyPartId(it)
            }
        }


        bodyPartTextView.text = bodyPart + " Workouts"



        button.setOnClickListener {
            showAddWorkoutDialog(bodyPart)

        }
    }

    private fun showAddWeightDialog(weight: String) {
        Toast.makeText(
            this,
            "Add weight for ${weight}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showAddWorkoutDialog(bodyPart: String?) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_workout, null)
        dialogBuilder.setView(dialogView)


        val alertDialog = dialogBuilder.create()


        val submitButton: Button = dialogView.findViewById(R.id.submit_button)
        val editTextWorkoutName: EditText = dialogView.findViewById(R.id.workoutname)
        val weightEdittext: EditText = dialogView.findViewById(R.id.weight)

        alertDialog.show()
        submitButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {

                    val bodyPartName = intent.getStringExtra("BODY_PART")
                    val bodyPartId = getWorkoutIdFromBodyPart(bodyPartName)

                    if (bodyPartId != null) {
                        val exercise = Workout(
                            workoutName = editTextWorkoutName.text.toString(),
                            bodyPartId = bodyPartId
                        )
                        val workoutlog = WorkoutLog(
                            weight = weightEdittext.text.toString().toFloatOrNull() ?: 0.0f,
                            date = getCurrentTimeInEpoch(),
                            workoutId = 1
                        )

                        database.workoutDao().insertWorkout(exercise)
                        database.workoutLogDao().insertWorkoutLog(workoutlog)
                        alertDialog.dismiss()
                    } else {

                    }
                }
            }
        }


    }

    private fun getWorkoutIdFromBodyPart(bodyPart: String?): Int {
        // Implement the logic to convert bodyPart to workoutId
        //TODO
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