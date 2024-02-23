package org.altbeacon.beaconreference

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }
    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    fun monitorizeButtonTapped(view: View){
        val intent = Intent(this, MonitorizarActivity::class.java)
        startActivity(intent)
    }
    fun mapButtonTapped(view: View) {
        val intent = Intent(this, MapaActivity::class.java)
        startActivity(intent)
    }

    fun loginButtonTapped(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        Log.d("TOKEN", "HOLA")
        startActivity(intent)
    }

    companion object {
        val TAG = "MainActivity"
        val PERMISSION_REQUEST_BACKGROUND_LOCATION = 0
        val PERMISSION_REQUEST_BLUETOOTH_SCAN = 1
        val PERMISSION_REQUEST_BLUETOOTH_CONNECT = 2
        val PERMISSION_REQUEST_FINE_LOCATION = 3
    }

}
