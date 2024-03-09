package org.altbeacon.beaconreference

import MyMonitorNotifier
import SharedPreferencesManager
import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Identifier
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.RangeNotifier
import org.altbeacon.beacon.Region


class BeaconReferenceApplication: Application() {
    // the region definition is a wildcard that matches all beacons regardless of identifiers.
    // if you only want to detect beacons with a specific UUID, change the id1 paremeter to
    // a UUID like Identifier.parse("2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6")

    //Podría tener en Mongo un montón de beacons y llamar a la api y crear todas las regiones, así si creo
    // un método para añadir y borrar regiones desde fuera no tendría que tocar la aplicación
    var region = Region("A0.12", Identifier.parse("DF7E1C79-43E9-44FF-886F-1D1F7DA6997A", 16),null, null)
    var region1 = Region("B0.11",  Identifier.parse("DF7E1C79-43E9-44FF-886F-1D1F7DA6997C", 16), null, null)

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)

        val beaconManager = BeaconManager.getInstanceForApplication(this)
        BeaconManager.setDebug(true)

        beaconManager.getBeaconParsers().clear()
        val parser = BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        beaconManager.getBeaconParsers().add(parser)


        val myMonitorNotifier = MyMonitorNotifier(this)
        beaconManager.addMonitorNotifier(myMonitorNotifier)

        BeaconManager.setBeaconSimulator(TimedBeaconSimulator())
        (BeaconManager.getBeaconSimulator() as TimedBeaconSimulator?)!!.createTimedSimulatedBeacons()

        beaconManager.setEnableScheduledScanJobs(false)
        beaconManager.setBackgroundBetweenScanPeriod(0)
        beaconManager.setBackgroundScanPeriod(500)

        for (region in beaconManager.monitoredRegions) {
            beaconManager.stopMonitoring(region)
        }

        beaconManager.startMonitoring(region)
        beaconManager.startMonitoring(region1)



        BeaconManager.setBeaconSimulator(TimedBeaconSimulator())
        (BeaconManager.getBeaconSimulator() as TimedBeaconSimulator).createTimedSimulatedBeacons()



    }
    companion object {
        val TAG = "BeaconReference"
    }

}