package com.pliamdev.pliam.roversensors;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.notification.StatusBarNotification;

public class ReferrerCatcher extends BroadcastReceiver {
    private String CHANNEL_ID = "com.pliamdev.pliam.rover_sensors";
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preferences_string), Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("quick_launch",true)) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null && !isNotificationVisible(notificationManager)) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    createNotificationChannel(notificationManager);
                    Intent oreoNotificationIntent = new Intent(context, SplashScreen.class);
                    oreoNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent oreoPendingIntent = PendingIntent.getActivity(context, 0, oreoNotificationIntent, PendingIntent.FLAG_IMMUTABLE);
                    Notification oreoNotification = new Notification.Builder(context,CHANNEL_ID).setContentTitle("Quick Launch").setContentText("Open \"Rover Sensors\"").setSmallIcon(R.drawable.ic_notify).setOngoing(true).setContentIntent(oreoPendingIntent).build();
                    notificationManager.notify(0, oreoNotification);
                }

                Intent notificationIntent = new Intent(context, SplashScreen.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
                Notification notification = new Notification.Builder(context).setContentTitle("Quick Launch").setContentText("Open \"Rover Sensors\"").setSmallIcon(R.drawable.ic_notify).setOngoing(true).setContentIntent(pendingIntent).build();
                notificationManager.notify(0, notification);
            }
        }else{
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null && isNotificationVisible(notificationManager)) {
                notificationManager.cancel(0);
            }
        }
    }
    private boolean isNotificationVisible(NotificationManager notificationManager){
        StatusBarNotification[] notifications = notificationManager.getActiveNotifications();
        for (StatusBarNotification notification : notifications){
            if(notification.getId() == 0){
                return true;
            }
        }
        return false;
    }
    private void createNotificationChannel(NotificationManager notificationManager){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Rover Sensors", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Rover Sensor app's notification channel");
            notificationManager.createNotificationChannel(channel);
        }
    }
}
