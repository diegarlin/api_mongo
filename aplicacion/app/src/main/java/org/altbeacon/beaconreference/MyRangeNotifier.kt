package org.altbeacon.beaconreference

import android.content.Context
import android.util.Log
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.RangeNotifier
import org.altbeacon.beacon.Region

class MyRangeNotifier (private val context: Context) : RangeNotifier {
    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        beacons?.forEach { beacon ->
            val distance = beacon.distance
            if (distance < 0.8) {
                Log.d("RANGE", "MENOS DE 1 METRO")
            }
        }
    }
}