package pontinisistemas.doctorbrewer.base.ext

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

object NotificationHelper {

    fun throwNotification(context: Context, id: Int, builder: NotificationCompat.Builder) {
        getNotificationManager(context).notify(id, builder.build())
    }

    fun closeNotifications(context: Context, id: Int) {
        getNotificationManager(context).cancel(id)
    }

    private fun getNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}
