package com.chelseatroy.canary.data

import com.chelseatroy.canary.data.Weather.CLOUDY
import org.junit.Assert.*
import org.junit.Test

class MoodEntryTest {
    @Test
    fun getFormattedLogTime_putsMoodEntryTimeInLegibleFormat() {
        assertEquals("Monday, 18 May, 2020, 10:36 AM",
            MoodEntry.getFormattedLogTime(1589816172396))
    }

    @Test
    fun formatForDatabase_putsMoodEntryPastimesInString_withEmptyDefault() {
        assertEquals("READING, SLEEP",
            MoodEntry.formatForDatabase(arrayListOf("READING", "SLEEP")))
        assertEquals("",
            MoodEntry.formatForDatabase(ArrayList()))

    }

    @Test
    fun formatForView_putsMoodEntryPastimesInString_withADefault() {
        assertEquals("READING, SLEEP",
            MoodEntry.formatPastimeForView(arrayListOf("READING", "SLEEP")))
        assertEquals("None",
            MoodEntry.formatPastimeForView(ArrayList()))
    }

    @Test
    fun toString_stringifiesMoodEntry() {
        var moodEntry = MoodEntry(
            Mood.ELATED,
            1589816172396,
            "Had a decent day!",
            "EATING, READING, SLEEP",
            "CLOUDY"
        )

        assertEquals("Mood Entry(loggedAt: Monday, 18 May, 2020, 10:36 AM, mood: ELATED, notes: Had a decent day!, pastimes: [EATING, READING, SLEEP], weather: CLOUDY)",
        moodEntry.toString())
    }


}