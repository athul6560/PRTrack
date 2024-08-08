package com.zeezaglobal.prtrack.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddExerciseActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = (application as MyApp).database
        setContentView(R.layout.activity_add_exercise)
        val button: TextView = findViewById(R.id.add_btn)
        val exerciseEditText: TextView = findViewById(R.id.editTextText)
        val weightEditText: TextView = findViewById(R.id.editTextNumber)




        button.setOnClickListener {
            val exerciseName = exerciseEditText.text.toString()
            val weight = weightEditText.text.toString().toIntOrNull() ?: 0
            // Insert a user
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                 /*   val exercise = Exercise(name = "John Doe", muscleGroup = "Chest", muscleGroupId = 1)
                    database.exerciseDao().insertExercise(exercise)*/
                }
            }


        }


    }
}