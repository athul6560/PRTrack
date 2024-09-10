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
import com.zeezaglobal.prtrack.Repositories.BodyPartRepository
import com.zeezaglobal.prtrack.Repositories.WorkoutRepository
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import com.zeezaglobal.prtrack.Utils.getCurrentTimeInEpoch
import com.zeezaglobal.prtrack.ViewModelFactopryt.BodyPartViewModelFactory
import com.zeezaglobal.prtrack.ViewModelFactopryt.WorkoutViewModelFactory
import com.zeezaglobal.prtrack.ViewModels.BodyPartViewModel
import com.zeezaglobal.prtrack.ViewModels.CombinedViewModel
import com.zeezaglobal.prtrack.ViewModels.WorkoutViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BodyPartActivity : AppCompatActivity() {

    private lateinit var viewModel: CombinedViewModel
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var bodyPartViewModel: BodyPartViewModel
    private lateinit var database: AppDatabase
    private lateinit var workoutLogAdapter: WorkoutLogAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_body_part)
        //view declaration
        val bodyPartTextView: TextView = findViewById(R.id.heading)
        val addNewWorkoutButton: TextView = findViewById(R.id.button)
//object creation
        val workoutRepository = WorkoutRepository((application as MyApp).database.workoutDao())
        val bodyPartRepository = BodyPartRepository((application as MyApp).database.bodyPartDao())
        val factory = WorkoutViewModelFactory(workoutRepository)
        workoutViewModel = ViewModelProvider(this, factory).get(WorkoutViewModel::class.java)
        val bodyPartViewModelFactory = BodyPartViewModelFactory(bodyPartRepository)
         bodyPartViewModel = ViewModelProvider(this, bodyPartViewModelFactory).get(
            BodyPartViewModel::class.java)



        database = (application as MyApp).database


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
                        workoutViewModel.insertWorkout(exercise)
                        val workoutlog = WorkoutLog(
                            weight = weightEdittext.text.toString().toFloatOrNull() ?: 0.0f,
                            date = getCurrentTimeInEpoch(),
                            workoutId = getWorkoutIdusingWorkout(exercise.workoutName)
                        )


                       // database.workoutLogDao().insertWorkoutLog(workoutlog)
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

    private suspend fun getWorkoutIdFromBodyPart(bodyPart: String?): Int? {
        return if (bodyPart != null) {
            withContext(Dispatchers.IO) {
                // Fetch bodyPartId using the ViewModel
                val bodyPartId = bodyPartViewModel.getBodyPartIdByNameSuspend(bodyPart)
                bodyPartId?.let {
                    // Return the found body part ID
                    it
                } ?: run {
                    // Return null if the body part is not found
                    null
                }
            }
        } else {
            null
        }
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