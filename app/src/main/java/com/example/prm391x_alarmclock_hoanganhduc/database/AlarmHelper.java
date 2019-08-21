package com.example.prm391x_alarmclock_hoanganhduc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "alarm.db";
    private static final int VERSION = 1;
    public AlarmHelper(Context context ) {
        super(context, DATABASE_NAME, null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table alarm(id integer primary key autoincrement, title text, hour integer, minute integer, is_active integer)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
