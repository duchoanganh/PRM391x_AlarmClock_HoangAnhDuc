package com.example.prm391x_alarmclock_hoanganhduc.util;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.prm391x_alarmclock_hoanganhduc.Alarm;
import com.example.prm391x_alarmclock_hoanganhduc.receiver.AlarmReceiver;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmUtil {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setAlarm(Context context, Alarm alarm) {//Đặt Alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.KEY_ALARM, alarm);
        intent.putExtra(Const.KEY_BUNDLE,bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getAlarmId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 2);

        alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void cancelAlarm(Context context, int alarmId) {//Hủy Alarm
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (pendingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }
}
