package com.ziasy.xmppchatapplication.common;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.support.v4.app.NotificationCompat;

import com.ziasy.xmppchatapplication.R;


public class AlwaysRunning extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;


      /*  Intent notificationIntent = new Intent(context, .class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);*/

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("UD Talks")
                .setContentText("Waiting for responce")
               //  .setContentIntent(pendingIntent)
                .build();

        startForeground(1337, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
}