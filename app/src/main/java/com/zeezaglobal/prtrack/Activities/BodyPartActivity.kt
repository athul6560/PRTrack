package com.zeezaglobal.prtrack.Activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeezaglobal.prtrack.R

class BodyPartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_body_part)
        val bodyPartTextView: TextView = findViewById(R.id.heading)

        // Retrieve the body part information from the intent
        val bodyPart = intent.getStringExtra("BODY_PART")

        // Display the body part information in the TextView
        bodyPartTextView.text = bodyPart ?: "No body part information available"
    }
}