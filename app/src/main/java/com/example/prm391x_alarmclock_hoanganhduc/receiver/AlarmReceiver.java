package com.example.prm391x_alarmclock_hoanganhduc.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.prm391x_alarmclock_hoanganhduc.Alarm;
import com.example.prm391x_alarmclock_hoanganhduc.AlarmService;
import com.example.prm391x_alarmclock_hoanganhduc.MainActivity;
import com.example.prm391x_alarmclock_hoanganhduc.R;
import com.example.prm391x_alarmclock_hoanganhduc.util.Const;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm running..", Toast.LENGTH_SHORT).show();
        Intent mediaIntent = new Intent(context, AlarmService.class);
        context.startService(mediaIntent);
        Bundle bundle = intent.getBundleExtra(Const.KEY_BUNDLE);
        Alarm alarm = (Alarm) bundle.getSerializable(Const.KEY_ALARM);
        sendNotification(context, alarm);
    }

    public void sendNotification(Context context, Alarm alarm) {//Hiện thông baó trên thanh Notify
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Alarm");
        builder.setContentText("This is alarm");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyChanel", "Alarm", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is chanel of alarm");
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channel.getId());
        }

        Notification notification = builder.build();

        notificationManager.notify(alarm.getAlarmId(), notification);
    }
}
