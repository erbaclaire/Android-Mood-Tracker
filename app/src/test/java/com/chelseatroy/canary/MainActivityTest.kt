package com.chelseatroy.canary

import android.widget.EditText
import com.chelseatroy.canary.ui.main.MoodEntryFragment
import org.junit.Assert.*

import com.google.android.material.floatingactionbutton.FloatingActionButton

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Test
    fun attachesFragmentsToProperTabs() {
        val systemUnderTest = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .visible()
            .get()
        val fixedActionButton = systemUnderTest.findViewById<FloatingActionButton>(R.id.add_entry_button)

        fixedActionButton.performClick()

        val moodEntryFragment = systemUnderTest.supportFragmentManager.findFragmentByTag("mood_entry");

        assertNotNull(moodEntryFragment);
        assertEquals(MoodEntryFragment::class, moodEntryFragment!!::class)
    }
}