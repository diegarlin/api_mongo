import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.Region
import java.util.*

class MyMonitorNotifier(private val context: Context) : MonitorNotifier {

    override fun didEnterRegion(region: Region) {
        val fecha = Calendar.getInstance()
        Log.d("NOTIFIER", "ENTRADA $fecha")
        sendNotification("Hola", "¡Bienvenido!")
    }

    override fun didExitRegion(region: Region) {
        val fecha = Calendar.getInstance()
        Log.d("NOTIFIER", "SALIDA $fecha")
    }

    override fun didDetermineStateForRegion(state: Int, region: Region) {
        // Este método puede ser útil para manejar otros estados si es necesario
    }

    private fun sendNotification(title: String, message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "BeaconReference_Channel"
        val channelName = "BeaconReference_Channel_Name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(notificationChannel)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(Random().nextInt(), builder.build())
    }
}
