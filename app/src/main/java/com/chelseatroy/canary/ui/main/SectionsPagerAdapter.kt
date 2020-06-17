package com.chelseatroy.canary.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.chelseatroy.canary.R

private val TAB_TITLES = arrayOf(
        R.string.tags,
        R.string.trends,
        R.string.history,
        R.string.profile
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        var fragmentToPresent = when (position) {
            0 -> TagsFragment.newInstance()
            1 -> TrendsFragment.newInstance(position + 1)
            2 -> HistoryFragment.newInstance(position + 1)
            3 -> ProfileFragment.newInstance(position + 1)
            else -> ProfileFragment.newInstance(position + 1)
        }

        return fragmentToPresent
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 4
    }
}