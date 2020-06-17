package com.chelseatroy.canary.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.chelseatroy.canary.R
import com.chelseatroy.canary.data.Mood
import com.chelseatroy.canary.data.MoodEntry
import com.chelseatroy.canary.data.MoodEntrySQLiteDBHelper
import com.chelseatroy.canary.data.MoodEntryScatterAnalysis
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


class TrendsFragment : Fragment() {

    lateinit var scatterPlot: ScatterChart
    lateinit var trendCommentary: TextView

    lateinit var moodEntries: List<MoodEntry>
    lateinit var databaseHelper: MoodEntrySQLiteDBHelper
    lateinit var markerView: ScatterplotMarkerView

    lateinit var scatterAnalysis: MoodEntryScatterAnalysis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_trends, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        scatterPlot = view?.findViewById(R.id.line_chart)!!
        trendCommentary = view?.findViewById(R.id.trend_commentary)!!

        databaseHelper = MoodEntrySQLiteDBHelper(activity)

        moodEntries = ArrayList()
        (moodEntries as ArrayList<MoodEntry>).clear()
        (moodEntries as ArrayList<MoodEntry>).addAll(
            databaseHelper.fetchMoodData(limitToPastWeek = true)
        )

        scatterAnalysis = MoodEntryScatterAnalysis()

        if (moodEntries.isNotEmpty()) {
            moodEntries = moodEntries.reversed().toList()
            arrangeScatterPlot()
        }
        trendCommentary.text = scatterAnalysis.commentOn(moodEntries)
    }

    private fun arrangeScatterPlot() {
        var xPositions = scatterAnalysis.getXPositionsFor(moodEntries)

        val dataSet = ArrayList<Entry>()

        for ((index, entry) in moodEntries.withIndex()) {
            dataSet.add(
                Entry(
                    xPositions.get(index),
                    scatterAnalysis.getYPositionFor(entry),
                    getIconFor(entry),
                    MoodEntry.getFormattedLogTime(entry.loggedAt)
                )
            )
        }

        val scatterDataSet = ScatterDataSet(dataSet, "")
        scatterDataSet.setDrawValues(false)

        scatterPlot.data = ScatterData(scatterDataSet)
        scatterPlot.notifyDataSetChanged()
        scatterPlot.invalidate()

        scatterPlot.axisRight.isEnabled = false
        scatterPlot.axisLeft.isEnabled = false
        scatterPlot.xAxis.isEnabled = false
        scatterPlot.description.text = ""
        scatterPlot.legend.isEnabled = false
        scatterPlot.setBackgroundColor(activity?.resources!!.getColor(R.color.canaryBackgroundColor)!!)

        scatterPlot.setTouchEnabled(true)
        scatterPlot.setPinchZoom(true)

        scatterPlot.setNoDataText("No moods entered yet!")

        markerView = ScatterplotMarkerView(activity, R.layout.view_marker_scatterplot)
        scatterPlot.setMarkerView(markerView)


        scatterPlot.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(
                e: Entry,
                h: Highlight?
            ) {
                scatterPlot.highlightValue(h)
                markerView.moodEntryLabel.text = e.data.toString()
            }

            override fun onNothingSelected() {}
        })

    }

    private fun getIconFor(moodEntry: MoodEntry): BitmapDrawable {
        var face = getResources().getDrawable(R.drawable.neutral)
        when (moodEntry.mood) {
            Mood.UPSET -> face = getResources().getDrawable(R.drawable.upset);
            Mood.DOWN -> face = getResources().getDrawable(R.drawable.down);
            Mood.NEUTRAL -> face = getResources().getDrawable(R.drawable.neutral);
            Mood.COPING -> face = getResources().getDrawable(R.drawable.coping);
            Mood.ELATED -> face = getResources().getDrawable(R.drawable.elated);
        }
        val bitmap = (face as BitmapDrawable).bitmap
        val imageresource =
            BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 30, 30, true))
        return imageresource
    }

    companion object {
        @JvmStatic
        fun newInstance(sectionNumber: Int): TrendsFragment {
            return TrendsFragment()
        }
    }
}