package com.chelseatroy.canary

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.chelseatroy.canary.data.Weather
import com.chelseatroy.canary.ui.main.HistoryFragment
import com.chelseatroy.canary.ui.main.MoodEntryFragment
import com.chelseatroy.canary.ui.main.ProfileFragment
import com.chelseatroy.canary.ui.main.SectionsPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), Updatable {
    lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    lateinit var viewPager: ViewPager
    lateinit var dialogFragment: MoodEntryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        viewPager = findViewById(R.id.view_pager) as ViewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val floatingActionButton: FloatingActionButton = findViewById(R.id.add_entry_button)
        floatingActionButton.setOnClickListener { view ->
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val existingMoodEntryFragment = supportFragmentManager.findFragmentByTag(getString(R.string.mood_entry_fragment_tag))
            if (existingMoodEntryFragment != null) {
                fragmentTransaction.remove(existingMoodEntryFragment)
            }
            fragmentTransaction.addToBackStack(null)
            dialogFragment = MoodEntryFragment()
            dialogFragment.dismissListener = this
            dialogFragment.show(fragmentTransaction, getString(R.string.mood_entry_fragment_tag))
        }

        //CHANGING THE TEXT OF THE TOP BAR
        val toolbarTitle = findViewById<TextView>(R.id.title)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var name = preferences.getString("Name", "")
        if (!name.equals("", ignoreCase = true)) {
            toolbarTitle.text = getString(R.string.greeting, name)
        }
    }

    override fun onDismissal() {
        val currentFragment =
            supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
        currentFragment?.onResume()
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_rainy -> if (checked) {
                    dialogFragment.weather = Weather.RAINY
                }
                R.id.radio_sunny -> if (checked) {
                    dialogFragment.weather = Weather.SUNNY
                }
                R.id.radio_cloudy -> if (checked) {
                    dialogFragment.weather = Weather.CLOUDY
                }
            }
        }
    }
}


