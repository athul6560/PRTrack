package com.zeezaglobal.prtrack.Activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddWorkoutActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = (application as MyApp).database
        setContentView(R.layout.activity_add_exercise)
        val button: TextView = findViewById(R.id.add_btn)
        val exerciseEditText: TextView = findViewById(R.id.editTextText)





        button.setOnClickListener {

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {

                    val bodyPartName = intent.getStringExtra("BODY_PART")
                    val bodyPartId = getBodyPartIdUsingIntent(bodyPartName)

                    if (bodyPartId != null) {
                        val exercise = Workout(
                            workoutName = exerciseEditText.text.toString(),
                            bodyPartId = bodyPartId
                        )

                        database.workoutDao().insertWorkout(exercise)
                    } else {

                    }
                }
            }


        }


    }

    private suspend fun getBodyPartIdUsingIntent(bodyPartName: String?): Int? {
        return bodyPartName?.let { name ->
            withContext(Dispatchers.IO) {
                database.bodyPartDao().getBodyPartIdByName(name)
            }
        }
    }
}