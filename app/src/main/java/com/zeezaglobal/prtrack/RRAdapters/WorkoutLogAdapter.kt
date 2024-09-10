package com.zeezaglobal.prtrack.RRAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.prtrack.Entities.WorkoutLogWithName
import com.zeezaglobal.prtrack.Entities.WorkoutWithLogs
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.Views.createLineChartView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkoutLogAdapter (
    private var workoutLogs: List<WorkoutWithLogs>,
    private val context: Context,
    private val onAddWeightClick: (String,String) -> Unit
) : RecyclerView.Adapter<WorkoutLogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workoutWithLogs = workoutLogs[position]

        // Set workout name
        holder.workoutTextView.text = workoutWithLogs.workout.workoutName

        // Transform logs into data points for the chart
        val dataPoints = transformLogsToDataPoints(workoutWithLogs)
        // Add click listener to the button to show popup
        holder.addWeightButton.setOnClickListener {
            showAddWeightPopup(workoutWithLogs.workout.workoutName)
        }
        // Create the line chart
        createLineChartView(
            context = context,
            parent = holder.chartContainer,
            dataPoints = dataPoints,
            maxDataPointY = 100f,
            xAxisColor = Color.LTGRAY,
            yAxisColor = Color.LTGRAY,
            gridColor = Color.LTGRAY,
            barColor = ContextCompat.getColor(context, R.color.blue),
            yAxisSteps = 5
        )
    }

    private fun showAddWeightPopup(workoutName: String) {
        // Create a dialog
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
                onAddWeightClick(enteredWeight,workoutName)
                dialog.dismiss()
            } else {
                weightInput.error = "Please enter a valid weight"
            }
        }

        // Show the dialog
        dialog.show()
    }

    private fun transformLogsToDataPoints(workoutWithLogs: WorkoutWithLogs): List<Pair<String, Float>> {
        return workoutWithLogs.workoutLogs.map {
            val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(Date(it.date))
            Pair(dayOfWeek, it.weight)
        }
    }

    override fun getItemCount(): Int = workoutLogs.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chartContainer: ViewGroup = itemView.findViewById(R.id.chartContainer)
        val workoutTextView: TextView = itemView.findViewById(R.id.workout_textview)
        val addWeightButton: Button = itemView.findViewById(R.id.add_weight_button)
    }

    fun updateData(newWorkoutLogs: List<WorkoutWithLogs>) {
        workoutLogs = newWorkoutLogs
        notifyDataSetChanged() // Notify the adapter to refresh the data
    }
}