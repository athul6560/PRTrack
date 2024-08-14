package com.zeezaglobal.prtrack.RRAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.prtrack.R

class WorkoutPagerAdapter(private val context: Context) : RecyclerView.Adapter<WorkoutPagerAdapter.PagerViewHolder>() {

    private val layouts = listOf(
        R.layout.page_workout_name,  // The layout file for the workout name entry
        R.layout.page_weight_picker   // The layout file for the weight selection
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(context).inflate(layouts[viewType], parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        // Bind any data if needed
    }

    override fun getItemCount(): Int = layouts.size

    override fun getItemViewType(position: Int): Int = position

    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
