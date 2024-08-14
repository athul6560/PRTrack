package com.zeezaglobal.prtrack.RRAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.prtrack.Entities.WorkoutLogWithName
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.Views.createLineChartView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkoutLogAdapter (
    private var workoutLogs: List<WorkoutLogWithName>,
    private val context: Context
) : RecyclerView.Adapter<WorkoutLogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val log = workoutLogs[position]
        holder.workoutTextView.text = log.workoutName

        val dataPoints = transformLogsToDataPoints(workoutLogs)


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

    private fun transformLogsToDataPoints(workoutLogs: List<WorkoutLogWithName>): List<Pair<String, Float>> {
        return workoutLogs.map {
            val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(Date(it.workoutLog.date))
            Pair(dayOfWeek, it.workoutLog.weight.toFloat())
        }

    }

    override fun getItemCount(): Int = workoutLogs.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chartContainer: ViewGroup = itemView.findViewById(R.id.chartContainer)
        val workoutTextView: TextView = itemView.findViewById(R.id.workout_textview)
    }
    fun updateData(newWorkoutLogs: List<WorkoutLogWithName>) {
        workoutLogs = newWorkoutLogs
        notifyDataSetChanged() // Notify the adapter to refresh the data
    }
}