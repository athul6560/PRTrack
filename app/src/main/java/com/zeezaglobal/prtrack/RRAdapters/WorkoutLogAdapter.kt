package com.zeezaglobal.prtrack.RRAdapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import com.zeezaglobal.prtrack.R
import com.zeezaglobal.prtrack.Vies.createLineChartView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkoutLogAdapter (
    private val workoutLogs: List<WorkoutLog>,
    private val context: Context
) : RecyclerView.Adapter<WorkoutLogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val logs = workoutLogs[position]
        val dataPoints = transformLogsToDataPoints(logs)

        createLineChartView(
            context = context,
            parent = holder.chartContainer,
            dataPoints = dataPoints,
            maxDataPointY = 100f,
            xAxisColor = Color.BLACK,
            yAxisColor = Color.BLACK,
            gridColor = Color.LTGRAY,
            barColor = ContextCompat.getColor(context, R.color.teal),
            yAxisSteps = 5
        )
    }

    private fun transformLogsToDataPoints(logs: WorkoutLog): List<Pair<String, Float>> {
        return workoutLogs.map {
            val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(Date(it.date))
            Pair(dayOfWeek, it.weight.toFloat())
        }
    }

    override fun getItemCount(): Int = workoutLogs.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chartContainer: ViewGroup = itemView.findViewById(R.id.chartContainer)
    }
}