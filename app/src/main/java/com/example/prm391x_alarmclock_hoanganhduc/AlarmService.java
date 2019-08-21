package com.example.prm391x_alarmclock_hoanganhduc;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

import com.example.prm391x_alarmclock_hoanganhduc.util.Const;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private AlarmBroadcast alarmBroadcast;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this,R.raw.redalert);
        mediaPlayer.start();// Bật nhạc
        mediaPlayer.setLooping(false);
        alarmBroadcast = new AlarmBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.ACTION_STOP_ALARM);
        registerReceiver(alarmBroadcast, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmBroadcast);
    }

    private class AlarmBroadcast extends BroadcastReceiver {//Hiện Notify và bật nhạc

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Const.ACTION_STOP_ALARM)) {
                if (mediaPlayer != null) {
                    Bundle bundle = intent.getBundleExtra(Const.KEY_BUNDLE);
                    Alarm alarm = (Alarm) bundle.getSerializable(Const.KEY_ALARM);
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.cancel(alarm.getAlarmId());
                    mediaPlayer.stop();
                }
                stopSelf();
            }
        }
    }
}
