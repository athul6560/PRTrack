package com.zeezaglobal.prtrack.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.RRAdapters.WorkoutLogAdapter
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import com.zeezaglobal.prtrack.Utils.getCurrentTimeInEpoch
import com.zeezaglobal.prtrack.ViewModels.CombinedViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        val addNewWorkoutButton: TextView = findViewById(R.id.button)
        viewModel = ViewModelProvider(this).get(CombinedViewModel::class.java)
        // Retrieve the body part information from the intent
        val bodyPart = intent.getStringExtra("BODY_PART")

        val recyclerView: RecyclerView = findViewById(R.id.recycle_review)
        recyclerView.layoutManager = LinearLayoutManager(this)
        workoutLogAdapter = WorkoutLogAdapter(
            emptyList(),
            context = this
        ) { weight,workoutName ->
            // This code will run when the button in a RecyclerView item is clicked
            showAddWeightDialog(weight, workoutName)
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



        addNewWorkoutButton.setOnClickListener {
            showAddWorkoutDialog(bodyPart)

        }
    }

    private fun showAddWeightDialog(weight: String,name:String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val workoutId = getWorkoutIdusingWorkout(name) // Fetch the workoutId

                val workoutLog = WorkoutLog(
                    weight = weight.toFloat(),
                    date = getCurrentTimeInEpoch(),
                    workoutId = workoutId
                )
                database.workoutLogDao().insertWorkoutLog(workoutLog)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@BodyPartActivity, "Weight added successfully", Toast.LENGTH_SHORT).show()

                }
            }}
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
                        database.workoutDao().insertWorkout(exercise)
                        val workoutlog = WorkoutLog(
                            weight = weightEdittext.text.toString().toFloatOrNull() ?: 0.0f,
                            date = getCurrentTimeInEpoch(),
                            workoutId = getWorkoutIdusingWorkout(exercise.workoutName)
                        )


                        database.workoutLogDao().insertWorkoutLog(workoutlog)
                        alertDialog.dismiss()
                    } else {

                    }
                }
            }
        }


    }

    private fun getWorkoutIdusingWorkout(workoutName: String): Int {
        return database.workoutDao().getWorkoutIdByName(workoutName)
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
                //test
            }
        }
    }
}