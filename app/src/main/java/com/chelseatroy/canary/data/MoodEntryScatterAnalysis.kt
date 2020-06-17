package com.chelseatroy.canary.data

import android.util.Log
import kotlin.math.pow

class MoodEntryScatterAnalysis() {
    fun getYPositionFor(moodEntry: MoodEntry): Float {
        var height = 0f
        when (moodEntry.mood) {
            Mood.UPSET -> height = 1f
            Mood.DOWN -> height = 2f
            Mood.NEUTRAL -> height = 3f
            Mood.COPING -> height = 4f
            Mood.ELATED -> height = 5f
        }
        return height
    }

    fun getXPositionsFor(moodEntries: List<MoodEntry>): List<Float> {
        val latestPoint = moodEntries.last().loggedAt
        val earliestPoint = moodEntries.first().loggedAt
        val diff = latestPoint - earliestPoint

        if (diff == 0L) {
            return arrayListOf(0f) // If we don't do this, a singleton list with 0L in it becomes [NaN] for some reason
        } else {
            return moodEntries.map { (it.loggedAt.toFloat() - earliestPoint) / diff } as ArrayList<Float>
        }
    }

    fun commentOn(moodEntries: List<MoodEntry>): String {
        // determine if 1 outlier
        var elated = 0
        var coping = 0
        var neutral = 0
        var upset = 0
        var down = 0
        for (entry in moodEntries) {
            if (entry.mood == Mood.ELATED) { elated += 1 }
            else if (entry.mood == Mood.COPING) { coping += 1}
            else if (entry.mood == Mood.NEUTRAL) { neutral += 1}
            else if (entry.mood == Mood.UPSET) { upset += 1 }
            else if (entry.mood == Mood.DOWN) { down += 1 }
        }
        var commentString = ""
        if (moodEntries.count() <= 2) {
            commentString += "You don't have that many mood entries. Please enter more!"
        }
        else if (elated == moodEntries.count()-1 || coping == moodEntries.count()-1 || neutral == moodEntries.count()-1 || upset == moodEntries.count()-1 || down == moodEntries.count()-1) {
            commentString += "Your mood has been stable this week! Here are notes for the only outlier: "
            if (elated == 1) { commentString += (moodEntries.filter { it.mood == Mood.ELATED })[0].notes }
            if (coping == 1) { commentString += (moodEntries.filter { it.mood == Mood.COPING })[0].notes }
            if (neutral == 1) { commentString += (moodEntries.filter { it.mood == Mood.NEUTRAL })[0].notes }
            if (upset == 1) { commentString += (moodEntries.filter { it.mood == Mood.UPSET })[0].notes }
            if (down == 1) { commentString += (moodEntries.filter { it.mood == Mood.DOWN })[0].notes }
        }
        else {
            // determine if mood has changed over the week with slope of best fit line
            val xPositions = getXPositionsFor(moodEntries)
            var xBar = 0f
            for (x in xPositions) {
                xBar += x
            }
            xBar = xBar / moodEntries.count()
            var yBar = 0f
            for (moodEntry in moodEntries) {
                yBar += getYPositionFor(moodEntry)
            }
            yBar = yBar / moodEntries.count()

            var counter = 0
            var xYMultSum = 0f
            var xSqrSum = 0f
            for (moodEntry in moodEntries) {
                val xDiff = xPositions[counter] - xBar
                xSqrSum += xDiff.pow(2)
                counter += 1
                val yDiff = getYPositionFor(moodEntry) - yBar
                val xDiffYDiff = xDiff * yDiff
                xYMultSum += xDiffYDiff
            }

            val moodTrend = xYMultSum / xSqrSum
            Log.i("MOOD_TREND", moodTrend.toString())
            if (moodTrend < 0) {
                commentString += "Your mood has declined this week :("
            } else {
                commentString += "Your mood has improved this week :)"
            }
        }

        return commentString
    }
}