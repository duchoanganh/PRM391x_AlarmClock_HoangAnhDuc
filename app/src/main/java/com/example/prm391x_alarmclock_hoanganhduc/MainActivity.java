package com.example.prm391x_alarmclock_hoanganhduc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import com.example.prm391x_alarmclock_hoanganhduc.database.AlarmDBManager;
import com.example.prm391x_alarmclock_hoanganhduc.listener.OnAlarmCallback;
import com.example.prm391x_alarmclock_hoanganhduc.util.Const;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnAlarmCallback {
    private List<Alarm> listAlarm;
    private RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;
    private AlarmDBManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAlarm = new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("                        Alarm ");
        setSupportActionBar(toolbar);
        mappingView();
        manager = new AlarmDBManager(this);
        listAlarm.clear();
        listAlarm.addAll(manager.getAllAlarm());
        alarmAdapter.notifyDataSetChanged();
    }


    private void mappingView() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        alarmAdapter = new AlarmAdapter(this, listAlarm, this);
        recyclerView.setAdapter(alarmAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(this, AddAlarmActivity.class);
            intent.putExtra("add_edit", 1);
            startActivityForResult(intent, Const.REQUEST_ADD);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_ADD) {//Add new Alarm
            if (resultCode == RESULT_OK) {
                Alarm al = (Alarm) data.getSerializableExtra("alarmNew");
                listAlarm.add(al);
                alarmAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updateAlarm(Alarm alarm) {
        manager.updateAlarm(alarm);
    }

}



