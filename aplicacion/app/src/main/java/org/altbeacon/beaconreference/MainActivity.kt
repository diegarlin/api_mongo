package org.altbeacon.beaconreference

import SharedPreferencesManager
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.MonitorNotifier
import android.content.Intent
import org.altbeacon.beacon.permissions.BeaconScanPermissionsActivity

class MainActivity : Activity() {
    private lateinit var loginButton: Button
    private lateinit var logoutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)
        loginButton = findViewById<Button>(R.id.loginButton)
        logoutButton = findViewById<Button>(R.id.logoutButton)

        val savedToken = SharedPreferencesManager.getTokenFromSharedPreferences(this@MainActivity)
        if (!savedToken.isNullOrBlank()) {
            loginButton.visibility = View.GONE
            logoutButton.visibility = View.VISIBLE
        } else {
            loginButton.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE
        }

    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }
    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }


    fun mapButtonTapped(view: View) {
        val intent = Intent(this, MapaActivity::class.java)
        startActivity(intent)
    }

    fun loginButtonTapped(view: View) {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    fun logoutButtonTapped(view: View) {
        SharedPreferencesManager.clearTokenFromSharedPreferences(this@MainActivity)
        loginButton.visibility = View.VISIBLE
        logoutButton.visibility = View.GONE
    }
    companion object {
        val TAG = "MainActivity"
        val PERMISSION_REQUEST_BACKGROUND_LOCATION = 0
        val PERMISSION_REQUEST_BLUETOOTH_SCAN = 1
        val PERMISSION_REQUEST_BLUETOOTH_CONNECT = 2
        val PERMISSION_REQUEST_FINE_LOCATION = 3
    }

}
