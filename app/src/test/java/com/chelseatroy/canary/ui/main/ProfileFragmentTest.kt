package com.chelseatroy.canary.ui.main

import org.junit.Assert.*
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chelseatroy.canary.R
import com.chelseatroy.canary.data.PastimeAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun fragmentSetsUpRecyclerViewAdapter() {
        val scenario = launchFragment<ProfileFragment>()
        scenario.onFragment { fragment ->
            assertEquals(PastimeAdapter::class, fragment.recyclerView.adapter!!::class)
        }
    }

}
