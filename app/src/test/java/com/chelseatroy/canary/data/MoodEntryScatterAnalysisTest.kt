package com.chelseatroy.canary.data

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MoodEntryScatterAnalysisTest {
    lateinit var systemUnderTest: MoodEntryScatterAnalysis

    @Before
    fun setUp() {
        systemUnderTest = MoodEntryScatterAnalysis()
    }

    @Test
    fun arragesMoodsVertically_basedOnMoodHappiness() {
        assertEquals(1f, systemUnderTest.getYPositionFor(MoodEntry(Mood.UPSET)))
        assertEquals(2f, systemUnderTest.getYPositionFor(MoodEntry(Mood.DOWN)))
        assertEquals(3f, systemUnderTest.getYPositionFor(MoodEntry(Mood.NEUTRAL)))
        assertEquals(4f, systemUnderTest.getYPositionFor(MoodEntry(Mood.COPING)))
        assertEquals(5f, systemUnderTest.getYPositionFor(MoodEntry(Mood.ELATED)))
    }

    @Test
    fun arrangesMoodsHorizontally_proportionalToWhenTheyWereLogged() {
        val regularlySpacedMoodEntries = arrayListOf<MoodEntry>(
            MoodEntry(Mood.ELATED, 1L, "Note", "EATING", "SUNNY"),
            MoodEntry(Mood.ELATED, 2L, "Note", "EATING", "RAINY"),
            MoodEntry(Mood.ELATED, 3L, "Note", "EATING", "CLOUDY"),
            MoodEntry(Mood.ELATED, 4L, "Note", "EATING", "RAINY"),
            MoodEntry(Mood.ELATED, 5L, "Note", "EATING", "SUNNY")
        )

        assertEquals(arrayListOf(0.0f, 0.25f, 0.5f, 0.75f, 1.0f), systemUnderTest.getXPositionsFor(regularlySpacedMoodEntries))
    }

    @Test
    fun comments_whenThereArentManyMoodEntries_encourageMoreMoodLogging() {
        val oneMoodEntry = arrayListOf<MoodEntry>(
            MoodEntry(Mood.DOWN, 1L, "Note1", "READING", "SUNNY")
        )
        assertEquals("You don't have that many mood entries. Please enter more!", systemUnderTest.commentOn(oneMoodEntry))
//        fail("If the person hasn't logged their mood much, " +
//                "it wouldn't be very accurate to try to do any analysis." +
//                "So choose a minimum number of entries you think you need to make an analysis " +
//                "and produce a message encouraging more logging if there are fewer entries than that.")
    }

    @Test
    fun comments_whenMoodIsImproving_mentionThatMoodIsImproving() {
        val happierMoodEnntries = arrayListOf<MoodEntry>(
            MoodEntry(Mood.DOWN, 1L, "Note1", "READING", "SUNNY"),
            MoodEntry(Mood.UPSET, 2L, "Note2", "SLEEP", "RAINY"),
            MoodEntry(Mood.NEUTRAL, 3L, "Note3", "EATING", "SUNNY"),
            MoodEntry(Mood.ELATED, 4L, "Note4", "CARING_FOR_OTHERS", "CLOUDY"),
            MoodEntry(Mood.COPING, 4L, "Note5", "JOURNALING", "RAINY")
        )
        assertEquals("Your mood has improved this week :)", systemUnderTest.commentOn(happierMoodEnntries))
//        fail("If the person's mood, in general, is better lately than it was earlier in the week " +
//                "produce a message mentioning it. " +
//                "You get to decide how you want to measure this.")
    }

    @Test
    fun comments_whenMoodIsDeclining_mentionThatMoodIsDeclining() {
        val saderMoodEnntries = arrayListOf<MoodEntry>(
            MoodEntry(Mood.ELATED, 1L, "Note1", "READING", "CLOUDY"),
            MoodEntry(Mood.COPING, 2L, "Note2", "SLEEP", "SUNNY"),
            MoodEntry(Mood.DOWN, 3L, "Note3", "EATING", "RAINY"),
            MoodEntry(Mood.DOWN, 4L, "Note4", "CARING_FOR_OTHERS", "RAINY"),
            MoodEntry(Mood.DOWN, 4L, "Note5", "JOURNALING", "SUNNY")
        )
        assertEquals("Your mood has declined this week :(", systemUnderTest.commentOn(saderMoodEnntries))
//        fail("If the person's mood, in general, is not as good lately as it was earlier in the week " +
//                "produce a message mentioning it. " +
//                "You get to decide how you want to measure this.")
    }

    @Test
    fun comments_whenThereIsOneOutlierMood_mentionAnyNotesFromThatMood() {
        val oneOutlierMoodEnntries = arrayListOf<MoodEntry>(
            MoodEntry(Mood.DOWN, 1L, "Note1", "READING", "SUNNY"),
            MoodEntry(Mood.DOWN, 2L, "Note2", "SLEEP", "SUNNY"),
            MoodEntry(Mood.ELATED, 3L, "God, I'm so happy today! It is so nice out!", "EATING", "SUNNY"),
            MoodEntry(Mood.DOWN, 4L, "Note3", "CARING_FOR_OTHERS", "CLOUDY"),
            MoodEntry(Mood.DOWN, 4L, "Note4", "JOURNALING", "RAINY")
        )
        assertEquals("Your mood has been stable this week! Here are notes for the only outlier: God, I'm so happy today! It is so nice out!", systemUnderTest.commentOn(oneOutlierMoodEnntries))
//        fail("If the person's mood, in general, is stable, and there is ONE outlier, " +
//                "produce a message containing the notes for that mood." +
//                "You get to decide how you want to go about this.")
    }


}