package com.zeezaglobal.prtrack.Views

import android.app.ActionBar
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout

class RectangleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var activityLevel: Int = 0
        set(value) {
            field = value
            setBackgroundColorWithRoundedCorners(getColorForActivity(value))
        }

    fun setRectangleSize(width: Int, height: Int, margin: Int) {
        val params = GridLayout.LayoutParams()
        params.width = width
        params.height = height
        params.setMargins(margin, margin, margin, margin) // Set gap between elements
        layoutParams = params
    }

    private fun getColorForActivity(activity: Int): Int {
        return when (activity) {
            0 -> Color.parseColor("#000000") // Light blue (low activity)
            1 -> Color.parseColor("#B2EBF2")
            2 -> Color.parseColor("#80DEEA")
            3 -> Color.parseColor("#4DD0E1")
            4 -> Color.parseColor("#00BCD4") // Darker blue (high activity)
            else -> Color.BLACK
        }
    }
    private fun setBackgroundColorWithRoundedCorners(color: Int) {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.cornerRadius = 16f // Set corner radius (in pixels)
        drawable.setColor(color)
        background = drawable
    }
}