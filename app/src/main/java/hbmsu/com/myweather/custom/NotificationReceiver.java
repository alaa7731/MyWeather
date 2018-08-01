package hbmsu.com.myweather.custom;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.johnhiott.darkskyandroidlib.models.DataPoint;

import hbmsu.com.myweather.R;
import hbmsu.com.myweather.Utils;
import hbmsu.com.myweather.activities.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {
    DataPoint dataPoint;

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification.Builder builder = new Notification.Builder(context);
        dataPoint = Utils.getModel(intent.getStringExtra("object"), DataPoint.class);
        builder.setContentTitle(context.getString(R.string.weather) + " " + Utils.formatMilliToTime((int) dataPoint.getTime()));
        builder.setContentText(dataPoint.getSummary() + " ," + String.format(context.getString(R.string.degree),
                String.valueOf(dataPoint.getTemperatureMax()), Utils.getDegreeUnit()));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent notifyIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, notificationCompat);
    }
}
