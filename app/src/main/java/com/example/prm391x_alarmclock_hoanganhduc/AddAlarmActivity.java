package com.example.prm391x_alarmclock_hoanganhduc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm391x_alarmclock_hoanganhduc.database.AlarmDBManager;
import com.example.prm391x_alarmclock_hoanganhduc.util.AlarmUtil;
import com.example.prm391x_alarmclock_hoanganhduc.util.Const;

public class AddAlarmActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button btnAdd;
    private EditText edtTitle;
    private Alarm alarm;
    private AlarmDBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        Toolbar toolbar = findViewById(R.id.toolbar_main);//Thêm thanh toolbar
        dbManager = new AlarmDBManager(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        timePicker = findViewById(R.id.timePicker);
        alarm = new Alarm();

        btnAdd = findViewById(R.id.btn_add);
        edtTitle = findViewById(R.id.editText);
        int action = getIntent().getIntExtra("add_edit", 1);
        if (action == Const.ACTION_ADD) {
            actionBar.setTitle("          Add Alarm ");
            addAlarmHandle(action);
        } else if (action == Const.ACTION_EDIT) {
            actionBar.setTitle("Edit");
            addAlarmHandle(action);
        }
    }

    private void addAlarmHandle(final int action) {//Thêm Alarm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (edtTitle.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlarmActivity.this, "Please enter alarm title", Toast.LENGTH_SHORT).show();
                    return;
                }
                alarm.setHour(timePicker.getHour());
                alarm.setMinute(timePicker.getMinute());

                alarm.setSubject(edtTitle.getText().toString());
                alarm.setActive(true);
                boolean isSuccess = dbManager.insertAlarm(alarm);
                Intent intent = new Intent();
                if (isSuccess) {
                    intent.putExtra("alarmNew", alarm);
                    setResult(RESULT_OK, intent);
                    AlarmUtil.setAlarm(AddAlarmActivity.this, alarm);
                } else {
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        });
    }
}
