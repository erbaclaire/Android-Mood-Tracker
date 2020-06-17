package com.chelseatroy.canary.ui.main

import org.junit.Assert.*
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chelseatroy.canary.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendsFragmentTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun fragmentLaunches() {
        val scenario = launchFragment<TrendsFragment>()
        scenario.onFragment { fragment ->
            val sectionlabel = fragment.view!!.findViewById<TextView>(R.id.trends_label)
            assertEquals("Trends in My Mood", sectionlabel.text)
        }
    }
}
