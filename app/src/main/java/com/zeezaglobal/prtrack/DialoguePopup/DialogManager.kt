package com.zeezaglobal.prtrack.DialoguePopup

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.zeezaglobal.prtrack.R

class DialogManager(private val context: Context) {

    // Function to show the dialog
    fun showAddWeightDialog(
        workoutName: String,
        onWeightSubmitted: (String) -> Unit // Callback to handle weight submission
    ) {
        val dialog = android.app.Dialog(context)
        dialog.setContentView(R.layout.dialog_add_weight)

        // Get reference to dialog views
        val workoutTextView = dialog.findViewById<TextView>(R.id.dialog_workout_textview)
        val weightInput = dialog.findViewById<EditText>(R.id.dialog_weight_input)
        val submitButton = dialog.findViewById<Button>(R.id.dialog_submit_button)

        // Set workout name in the dialog
        workoutTextView.text = "Add weight for $workoutName"

        // Handle submit button click
        submitButton.setOnClickListener {
            val enteredWeight = weightInput.text.toString()
            if (enteredWeight.isNotEmpty()) {
                // Invoke callback with entered weight
                onWeightSubmitted(enteredWeight)
                dialog.dismiss()
            } else {
                weightInput.error = "Please enter a valid weight"
            }
        }

        // Show the dialog
        dialog.show()
    }
    fun showAddWorkoutDialog(
        bodyPartName: String?,
        onWorkoutSubmitted: (String, String) -> Unit // Callback with workout name, weight, and bodyPartId
    ) {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_add_workout, null)
        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()

        val submitButton: Button = dialogView.findViewById(R.id.submit_button)
        val editTextWorkoutName: EditText = dialogView.findViewById(R.id.workoutname)


        alertDialog.show()
        submitButton.setOnClickListener {
            val workoutName = editTextWorkoutName.text.toString()


            if (workoutName.isNotEmpty() ) {
                bodyPartName?.let { name ->
                    // Use the callback to pass the workout details back to the activity
                    onWorkoutSubmitted(workoutName,bodyPartName)
                    alertDialog.dismiss()
                } ?: run {
                    // Handle case where the body part is null
                    Toast.makeText(context, "Body part is missing", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (workoutName.isEmpty()) {
                    editTextWorkoutName.error = "Please enter a workout name"
                }
                       }
        }
    }
}