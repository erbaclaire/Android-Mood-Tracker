package com.chelseatroy.canary.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chelseatroy.canary.R
import com.chelseatroy.canary.data.MoodEntrySQLiteDBHelper
import com.chelseatroy.canary.data.PastimeAdapter


/**
 * A placeholder fragment containing a simple view.
 */
class ProfileFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: PastimeAdapter
    var pastimeEntries = ArrayList<String>()

    lateinit var databaseHelper: MoodEntrySQLiteDBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onResume() {
        super.onResume()

        recyclerView = view?.findViewById(R.id.pastime_list)!!

        databaseHelper = MoodEntrySQLiteDBHelper(activity)
        pastimeEntries = ArrayList<String>()

        pastimeEntries = fetchPastimeData()

        if (activity != null) {
            recyclerViewAdapter = PastimeAdapter(activity?.applicationContext!!, pastimeEntries, activity!!)
        }
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext!!)

        val logPastimeButton = view?.findViewById<Button>(R.id.add_pastime_button)
        val uniquePastime = view?.findViewById<EditText>(R.id.unique_pastime)

        logPastimeButton?.setOnClickListener({ view ->
            if (uniquePastime != null && uniquePastime.text.toString() != "") {
                submitPastimeEntry(uniquePastime)
                uniquePastime.getText().clear()
                onResume()
            }
        })
    }

    private fun submitPastimeEntry(uniquePastime: EditText) {
        databaseHelper.savePastime(uniquePastime.text.toString().toUpperCase())
    }

    private fun fetchPastimeData(): ArrayList<String>{
        val cursor = databaseHelper.listPastimeEntries()

        val fromPastimeColumn = cursor.getColumnIndex(MoodEntrySQLiteDBHelper.PASTIME_ENTRY_COLUMN)

        if(cursor.getCount() == 0) {
            Log.i("NO PASTIME ENTRIES", "Fetched data and found none.")
        } else {
            Log.i("PASTIME ENTRIES FETCHED", "Fetched data and found pastime entries.")
            pastimeEntries.clear()

            while (cursor.moveToNext()) {
                val nextPastime = cursor.getString(fromPastimeColumn)
                pastimeEntries.add(nextPastime.toString())
            }
        }
        return pastimeEntries
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): ProfileFragment {
            return ProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

    }
}