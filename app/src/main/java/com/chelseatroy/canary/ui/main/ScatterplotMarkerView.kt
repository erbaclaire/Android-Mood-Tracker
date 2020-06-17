package com.chelseatroy.canary.ui.main


import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.chelseatroy.canary.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.highlight.Highlight


class ScatterplotMarkerView(context: Context?, layoutResource: Int) :
    MarkerView(context, layoutResource) {
    val moodEntryLabel: TextView

    fun getXOffset(xpos: Float): Int = -(width / 2)
    fun getYOffset(ypos: Float): Int = -height

    init {
        moodEntryLabel = findViewById<View>(R.id.scatterplot_marker_label) as TextView
    }
}
