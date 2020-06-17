package com.chelseatroy.canary.data

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.ProxyFileDescriptorCallback
import android.provider.ContactsContract
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.chelseatroy.canary.BuildConfig
import com.chelseatroy.canary.MainActivity
import com.chelseatroy.canary.R
import com.chelseatroy.canary.ui.main.ProfileFragment

class PastimeAdapter(context: Context, pastimeEntries: ArrayList<String>, activity: FragmentActivity) :
    RecyclerView.Adapter<PastimeAdapter.PastimeEntryViewHolder>() {
    var context: Context
    var pastimeEntries: ArrayList<String>
    var activity: Activity

    init {
        this.context = context
        this.pastimeEntries = pastimeEntries
        this.activity = activity
    }

    inner class PastimeEntryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var pastimesTextView: TextView
        var deleteButton: ImageButton

        init {
            pastimesTextView = itemView.findViewById(R.id.pastime_item)
            deleteButton = itemView.findViewById(R.id.delete_button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastimeEntryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val pastimeEntryListItem: View = inflater
            .inflate(R.layout.pastime_item, parent, false)
        return PastimeEntryViewHolder(pastimeEntryListItem)
    }

    override fun getItemCount(): Int {
        return pastimeEntries.size
    }

    override fun onBindViewHolder(holder: PastimeEntryViewHolder, position: Int) {
        holder.pastimesTextView.text = pastimeEntries.get(position)
        holder.deleteButton.setOnClickListener({ view ->
            val builder = AlertDialog.Builder(this.activity)
            builder.setTitle("Are you sure you want to delete the activity " + holder.pastimesTextView.text.toString() + "?")
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                deletePastimeEntry(holder.pastimesTextView.text.toString())
                pastimeEntries.remove(holder.pastimesTextView.text.toString())
                notifyDataSetChanged()
            })
            builder.create().show()
        })
    }

    fun deletePastimeEntry(pastimeEntry: String) {
        val databaseHelper = MoodEntrySQLiteDBHelper(context)
        databaseHelper.deletePastime(pastimeEntry)
        Log.i("DELETED PASTIME", pastimeEntry)
    }

}
