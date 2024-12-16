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
            0 -> Color.parseColor("#E6EBFF") // Lightest blue (lowest activity)
            1 -> Color.parseColor("#A3C6FF")
            2 -> Color.parseColor("#6FAEFF")
            3 -> Color.parseColor("#2A91FF")
            4 -> Color.parseColor("#0A84FF")
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