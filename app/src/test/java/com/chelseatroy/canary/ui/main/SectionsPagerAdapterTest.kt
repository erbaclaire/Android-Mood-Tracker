package com.chelseatroy.canary.ui.main

import com.chelseatroy.canary.MainActivity
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SectionsPagerAdapterTest {
    @Before
    fun setUp() {
    }

    //Not exactly a Unit test because, rather than starting up my pager adapter in isolation,
    //I start the activity that uses the pager and then snag it with an instance variable.

    //The benefits here: easier than bending over backwards to isolate these objects.
    //The drawback: if this test fails, it could be due to a problem in the activity and have
    //              nothing to do with the pager adapter. (If you want to see this happen,
    //              add .resume() in between create() and visible() and note that the test fails
    //              even though I didn't change the pager adapter or assertions at all).

    //How I chose: I didn't want to spend a bunch of time unit testing code that I got for
    //             free from the Android Studio "Tabbed Activity" template.
    //             That's venturing kind of close to unit testing Android itself, which is a
    //             rather low return-on-investment activity because the Android team already
    //             does this (and has way more resources at their disposal to do it than I do).
    //
    //             Also, I'm not using this pager adapter anywhere in the app besides this activity.

    @Test
    fun attachesFragmentsToProperTabs() {
        val activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .visible()
            .get()
        val systemUnderTest = activity.sectionsPagerAdapter as SectionsPagerAdapter

        val firstTabFragment = systemUnderTest.getItem(0)
        assertEquals(TagsFragment::class, firstTabFragment::class)

        val secondTabFragment = systemUnderTest.getItem(1)
        assertEquals(TrendsFragment::class, secondTabFragment::class)

        val thirdTabFragment = systemUnderTest.getItem(2)
        assertEquals(HistoryFragment::class, thirdTabFragment::class)

        val fourthTabFragment = systemUnderTest.getItem(3)
        assertEquals(ProfileFragment::class, fourthTabFragment::class)
    }
}