package com.zeezaglobal.prtrack.Activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.zeezaglobal.prtrack.DialoguePopup.DialogManager
import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.RRAdapters.WorkoutLogAdapter
import com.zeezaglobal.prtrack.Repositories.BodyPartRepository
import com.zeezaglobal.prtrack.Repositories.WorkoutLogRepository
import com.zeezaglobal.prtrack.Repositories.WorkoutRepository
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import com.zeezaglobal.prtrack.ViewModelFactopryt.BodyPartViewModelFactory
import com.zeezaglobal.prtrack.ViewModels.BodyPartViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BodyPartActivity : AppCompatActivity() {


    private var id: Int = 0

    private lateinit var bodyPartViewModel: BodyPartViewModel
    private lateinit var database: AppDatabase
    private lateinit var workoutLogAdapter: WorkoutLogAdapter
    private val dialogManager by lazy { DialogManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_body_part)

        val bodyPartTextView: TextView = findViewById(R.id.heading)

        val addNewWorkoutButton: ExtendedFloatingActionButton =findViewById(R.id.add_new_workout_btn)
        val recyclerView: RecyclerView = findViewById(R.id.recycle_review)
//object creation
        val workoutRepository = WorkoutRepository((application as MyApp).database.workoutDao())
        val bodyPartRepository = BodyPartRepository((application as MyApp).database.bodyPartDao())
        val workoutLogRepository = WorkoutLogRepository((application as MyApp).database.workoutLogDao())

        val bodyPartViewModelFactory = BodyPartViewModelFactory(bodyPartRepository,workoutRepository,workoutLogRepository)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bodyPartViewModel = ViewModelProvider(this, bodyPartViewModelFactory).get(
            BodyPartViewModel::class.java
        )

        workoutLogAdapter = WorkoutLogAdapter(
            emptyList(),
            context = this
        ) {  workoutName ->
            // This code will run when the button in a RecyclerView item is clicked
            showAddWeightDialog(workoutName)
        }


        recyclerView.adapter = workoutLogAdapter


        // Retrieve the body part information from the intent
        val bodyPart = intent.getStringExtra("BODY_PART")


//Button Clicks
        addNewWorkoutButton.setOnClickListener {
            if (bodyPart != null) {
                showAddWorkoutDialog(bodyPart)
            }

        }

        database = (application as MyApp).database




        lifecycleScope.launch {
            val bodyPartId = getBodyPartIdUsingIntent(bodyPart)
            bodyPartId?.let {
                bodyPartViewModel.getWorkoutsWithLogsByBodyPartId(it)
                 id = it
            }
        }

        // Observe the LiveData
        bodyPartViewModel.workoutsWithLogs.observe(this, { workoutLogs ->
            workoutLogs?.let {
              //  bodyPartViewModel.getWorkoutsWithLogsByBodyPartId(id)
                workoutLogAdapter.updateData(workoutLogs)
                Log.d("Basic Testing", "onCreate: $workoutLogs")
            }
        })

        // Fetch the combined data



        bodyPartTextView.text = bodyPart + " Workouts"




    }


    private fun showAddWorkoutDialog(bodyPartName:String) {


        dialogManager.showAddWorkoutDialog(bodyPartName) { workoutName, bodyPartName ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {

                    val workout = getWorkoutIdFromBodyPart(bodyPartName)?.let {
                        Workout(
                            workoutName = workoutName,
                            bodyPartId = it
                        )
                    }
                    if (workout != null) {
                        bodyPartViewModel.insertWorkout(workout)
                    }



                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@BodyPartActivity,
                            "Workout added successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun getCurrentTimeInEpoch(): Long {
        // Logic to get the current time in epoch format
        return System.currentTimeMillis() / 1000
    }






    private fun showAddWeightDialog(name: String) {
        Log.d("Basic Testing", "showAddWeightDialog: $name")
        dialogManager.showAddWeightDialog(name) { enteredWeight ->
            // Handle weight submission
            saveWorkoutLog(enteredWeight, name)
        }


    }

    private fun saveWorkoutLog(enteredWeight: String, s: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val workoutId = getWorkoutIdusingWorkout(s) // Fetch the workoutId

                val workoutLog = WorkoutLog(
                    weight = enteredWeight.toFloat(),
                    date = getCurrentTimeInEpoch(),
                    workoutId = workoutId,
                    sets = 10
                )
                database.workoutLogDao().insertWorkoutLog(workoutLog)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@BodyPartActivity,
                        "Weight added successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

   /* private fun showAddWorkoutDialog(bodyPart: String?) {
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


    }*/

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