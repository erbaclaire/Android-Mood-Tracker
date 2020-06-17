package com.chelseatroy.canary.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.data.PieEntry

class MoodEntryPieAnalysis() {
    fun getActivitiesFrom(moodEntries: List<MoodEntry>): ArrayList<PieEntry> {
        val pieSections = ArrayList<PieEntry>()
        val activityCounts = mutableMapOf<String, Int>()
        var totalActivities = 0
        for (entry in moodEntries) {
            for (pastime in entry.recentPastimes) {
                if (pastime == "") {

                }
                else {
                    totalActivities += 1
                    if (activityCounts.containsKey(pastime)) {
                        activityCounts[pastime] = activityCounts.getOrDefault(pastime, 0) + 1
                    } else {
                        activityCounts[pastime] = 1
                    }
                }
            }
        }
        for (activityCount in activityCounts) {
            val activityPct = activityCount.value.toFloat() / totalActivities.toFloat() * 100F
            pieSections.add(PieEntry(activityPct, activityCount.key))
        }
        return pieSections
    }

    fun getMoodsFrom(moodEntries: List<MoodEntry>): ArrayList<PieEntry> {
        val pieSections = ArrayList<PieEntry>()
        val moodCounts = mutableMapOf<String, Int>()
        for (entry in moodEntries) {
            val moodString = entry.mood.toString()
            if (moodCounts.containsKey(moodString)) { moodCounts[moodString] = moodCounts.getOrDefault(moodString, 0) + 1 }
            else { moodCounts[moodString] = 1 }
        }
        for (moodCount in moodCounts) {
            Log.i(moodCount.key, moodCount.value.toString())
            val moodPct = moodCount.value.toFloat() / moodEntries.count().toFloat() * 100F
            pieSections.add(PieEntry(moodPct, moodCount.key))
        }
        return pieSections
    }
}