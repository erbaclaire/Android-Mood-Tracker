package com.chelseatroy.canary.data

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MoodEntry(mood: Mood) {

    var weather = Weather.UNKNOWN
    val mood = mood
    var notes: String = ""
    var loggedAt: Long = Calendar.getInstance().timeInMillis
    var recentPastimes = arrayListOf<String>()

    constructor(
        mood: Mood,
        createdTimeInMillis: Long,
        notes: String,
        recentPastimes: String?,
        weather: String
    ) : this(mood) {
        this.loggedAt = createdTimeInMillis
        this.notes = notes
        if(recentPastimes != null) {
            this.recentPastimes = hydrateFromDatabase(recentPastimes)
        } else {
            this.recentPastimes = ArrayList()
        }
        this.weather = Weather.valueOf(weather)
        Log.i("ENTRY CONSTRUCTED!", this.toString())
    }

    companion object {
        @JvmStatic
        fun getFormattedLogTime(loggedAt: Long): String? {
            val DATE_FORMAT = "EEEE, dd MMM, yyyy, hh:mm a"
            val dateFormat = SimpleDateFormat(DATE_FORMAT)
            dateFormat.setTimeZone(TimeZone.getDefault())
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = loggedAt
            return dateFormat.format(calendar.getTime())
        }

        @JvmStatic
        fun formatForDatabase(pastimes: ArrayList<String>): String {
            return pastimes.joinToString { it.toString() }
        }

        @JvmStatic
        fun hydrateFromDatabase(pastimes: String): ArrayList<String> {
            return ArrayList(
                pastimes.split(", ")
                    .map { it })
        }

        fun formatPastimeForView(pastimes: ArrayList<String>): String {
            val stringifiedPastimes = pastimes.joinToString { it.toString() }
            return if (stringifiedPastimes.isEmpty()) "None" else stringifiedPastimes
        }

        fun formatNotesForView(notes: String): String {
            return if (notes.isEmpty()) "None" else notes
        }
    }

    override fun toString(): String {
        return "Mood Entry(" +
                "loggedAt: ${getFormattedLogTime(this.loggedAt)}, " +
                "mood: ${this.mood}, " +
                "notes: ${this.notes}, " +
                "pastimes: ${this.recentPastimes}, " +
                "weather: ${this.weather})"
    }
}

enum class Weather {
    UNKNOWN, SUNNY, CLOUDY, RAINY
}

enum class Mood {
    UPSET, DOWN, NEUTRAL, COPING, ELATED
}
