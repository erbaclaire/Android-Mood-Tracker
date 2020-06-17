package com.chelseatroy.canary.data

import com.github.mikephil.charting.data.PieEntry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MoodEntryPieAnalysisTest {
    lateinit var systemUnderTest: MoodEntryPieAnalysis

    @Before
    fun setUp() {
        systemUnderTest = MoodEntryPieAnalysis()
    }

    @Test
    fun getMoodsFrom_moodPctBreakdownCorrect() {
        val moodEntries = arrayListOf<MoodEntry>(
            MoodEntry(Mood.DOWN, 1L, "Note1", "READING", "SUNNY"),
            MoodEntry(Mood.UPSET, 2L, "Note2", "SLEEP", "RAINY"),
            MoodEntry(Mood.DOWN, 3L, "Note3", "EATING", "SUNNY"),
            MoodEntry(Mood.ELATED, 4L, "Note4", "CARING_FOR_OTHERS", "CLOUDY"),
            MoodEntry(Mood.COPING, 5L, "Note5", "JOURNALING", "RAINY")
        )
        val pieEntries = arrayListOf(PieEntry(40.0f,"DOWN"), PieEntry(20.0f,"ELATED"),PieEntry(20.0f, "UPSET"), PieEntry(20.0f, "COPING")).toString().trim()
        assertEquals(pieEntries,systemUnderTest.getMoodsFrom(moodEntries).toString())
    }

    @Test
    fun getActivitiesFrom_activityPctBreakdownCorrect() {
        val moodEntries = arrayListOf<MoodEntry>(
            MoodEntry(Mood.DOWN, 1L, "Note1", "READING, SLEEP, EATING", "SUNNY"),
            MoodEntry(Mood.UPSET, 2L, "Note2", "SLEEP", "RAINY"),
            MoodEntry(Mood.DOWN, 3L, "Note3", "EATING, READING", "SUNNY"),
            MoodEntry(Mood.ELATED, 4L, "Note4", "CARING_FOR_OTHERS, JOURNALING", "CLOUDY"),
            MoodEntry(Mood.COPING, 5L, "Note5", "JOURNALING, EATING", "RAINY")
        )
        val pieEntries = arrayListOf(PieEntry(20.0f, "READING"), PieEntry(20.0f, "SLEEP"), PieEntry(30.000002f, "EATING"), PieEntry(10.0f, "CARING_FOR_OTHERS"), PieEntry(20.0f, "JOURNALING")).toString().trim()
        assertEquals(pieEntries,systemUnderTest.getActivitiesFrom(moodEntries).toString())
    }
}