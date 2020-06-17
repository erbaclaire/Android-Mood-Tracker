package com.chelseatroy.canary

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chelseatroy.canary.api.CanarySession
import com.chelseatroy.canary.api.MockyAPIImplementation
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signInButton = findViewById<Button>(R.id.sign_in_button)
        signInButton.setOnClickListener { view ->
            if (isNetworkConnected()) {
                progressBar.visibility = View.VISIBLE
                authenticate()
            } else {
                AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun authenticate() {
        val loginJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        val coroutineScope = CoroutineScope(loginJob + Dispatchers.Main)

        var session: CanarySession? = null
        coroutineScope.launch(errorHandler) {
            session = MockyAPIImplementation().authenticate()

            //HERE's WHERE WE HIDE THE PROGRESS BAR
            progressBar.visibility = View.GONE

            //DON'T ACTUALLY log people's authentication tokens in a production application
            Log.i("Session Retrieved:", session.toString())

            if (session?.token != null) {
                //HERE'S WHERE WE SAVE THE SESSION DAA
                saveSessionData(session!!.name, session!!.token)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                deliverAuthFailureMessage()
            }

        }
    }

    private fun deliverAuthFailureMessage() {
        AlertDialog.Builder(this).setTitle("Whoops")
            .setMessage("Wrong credentials. Try again?")
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert).show()

    }

    private fun saveSessionData(name: String?, token: String?) {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Name", name)
        editor.putString("Token", token)
        editor.apply()
    }
}


