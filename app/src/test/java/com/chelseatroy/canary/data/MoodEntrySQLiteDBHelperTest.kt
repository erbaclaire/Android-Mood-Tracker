package com.chelseatroy.canary.data

import org.junit.Assert.assertEquals
import org.junit.Test

class MoodEntrySQLiteDBHelperTest {

    // tests for mood entry
    @Test
    fun creationQuery_assemblesMoodEntryTable() {
        val expectedQuery = "CREATE TABLE mood_entry (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mood TEXT, " +
                "logged_at INTEGER, " +
                "notes TEXT, " +
                "weather TEXT, " +
                "pastimes TEXT);"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.createMoodEntriesTableQuery)
    }

    @Test
    fun deletionQuery_removesMoodEntryTable() {
        val expectedQuery = "DROP TABLE IF EXISTS mood_entry"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.dropMoodEntriesTableQuery)
    }

    // tests pastime creation and deletion
    @Test
    fun creationQuery_assemblesPastimeEntryTable() {
        val expectedQuery = "CREATE TABLE pastime_entry (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pastimes TEXT);"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.createPastimeEntriesTableQuery)
    }

    @Test
    fun deletionQuery_removesPastimeEntryTable() {
        val expectedQuery = "DROP TABLE IF EXISTS pastime_entry"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.dropPastimeEntriesTableQuery)
    }

    // tests that when pastime table is created, the included past times are included
    @Test
    fun generateExercisePastime() {
        val expectedQuery = "INSERT INTO pastime_entry (pastimes) VALUES ('EXERCISE');"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.generateExercisePastime)
    }
    @Test
    fun generateMeditationPastime() {
        val expectedQuery = "INSERT INTO pastime_entry (pastimes) VALUES ('MEDITATION');"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.generateMeditationPastime)
    }
    @Test
    fun generateSocialzingPastime() {
        val expectedQuery = "INSERT INTO pastime_entry (pastimes) VALUES ('SOCIALIZING');"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.generateSocializingPastime)
    }
    @Test
    fun generateHydratingPastime() {
        val expectedQuery = "INSERT INTO pastime_entry (pastimes) VALUES ('HYDRATING');"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.generateHydratingPastime)
    }
    @Test
    fun generateEatingPastime() {
        val expectedQuery = "INSERT INTO pastime_entry (pastimes) VALUES ('EATING');"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.generateEatingPastime)
    }
    @Test
    fun generateTelevisionPastime() {
        val expectedQuery = "INSERT INTO pastime_entry (pastimes) VALUES ('TELEVISION');"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.generateTelevisionPastime)
    }
    @Test
    fun generateReadingPastime() {
        val expectedQuery = "INSERT INTO pastime_entry (pastimes) VALUES ('READING');"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.generateReadingPastime)
    }
    @Test
    fun generateSleepPastime() {
        val expectedQuery = "INSERT INTO pastime_entry (pastimes) VALUES ('SLEEP');"
        assertEquals(expectedQuery, MoodEntrySQLiteDBHelper.generateSleepPastime)
    }
}