package com.example.prm391x_alarmclock_hoanganhduc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.prm391x_alarmclock_hoanganhduc.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmDBManager {
    private AlarmHelper alarmHelper;
    private SQLiteDatabase database;

    public AlarmDBManager(Context context) {
        alarmHelper = new AlarmHelper(context);
        database = alarmHelper.getWritableDatabase();
    }

    public boolean insertAlarm(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put("title", alarm.getSubject());
        values.put("hour", alarm.getHour());
        values.put("minute", alarm.getMinute());
        values.put("is_active", alarm.isActive());
        long id = database.insert("alarm", null, values);
        if (id > 0) {
            Log.d("lt", "insert success: id=" + id);
            return true;
        } else {
            Log.d("lt", "insert fail: id=" + id);
            return false;
        }
    }

    public void updateAlarm(Alarm alarm) {//Thêm Alarm
        ContentValues values = new ContentValues();
        values.put("id", alarm.getAlarmId());
        values.put("title", alarm.getSubject());
        values.put("hour", alarm.getHour());
        values.put("minute", alarm.getMinute());
        values.put("is_active", alarm.isActive());
        int rowEffected = database.update("alarm", values, "id=?", new String[]{String.valueOf(alarm.getAlarmId())});
    }


    public List<Alarm> getAllAlarm() {//Hien Thi Alarm
        List<Alarm> arrResult = new ArrayList<>();
        Cursor cursor = database.query("alarm", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int hourIndex = cursor.getColumnIndex("hour");
            int minuteIndex = cursor.getColumnIndex("minute");
            int activeIndex = cursor.getColumnIndex("is_active");

            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            int hour = cursor.getInt(hourIndex);
            int minute = cursor.getInt(minuteIndex);
            int active = cursor.getInt(activeIndex);

            Alarm alarm = new Alarm(id, title, hour, minute, active == 1 ? true : false);
            arrResult.add(alarm);
        }
        return arrResult;
    }

}
