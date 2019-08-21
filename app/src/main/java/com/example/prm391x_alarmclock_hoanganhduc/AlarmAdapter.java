package com.example.prm391x_alarmclock_hoanganhduc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.prm391x_alarmclock_hoanganhduc.listener.OnAlarmCallback;
import com.example.prm391x_alarmclock_hoanganhduc.util.AlarmUtil;
import com.example.prm391x_alarmclock_hoanganhduc.util.Const;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.MyViewHolder> {
    private Context context;
    private List<Alarm> list_alarm;
    private OnAlarmCallback onAlarmCallback;

    public AlarmAdapter(Context context, List<Alarm> list_alarm, OnAlarmCallback onAlarmCallback) {
        this.context = context;
        this.list_alarm = list_alarm;
        this.onAlarmCallback = onAlarmCallback;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvtHour, tvtSubject;
        ToggleButton tglAlarm;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvtHour = itemView.findViewById(R.id.time);
            tvtSubject = itemView.findViewById(R.id.subject);
            tglAlarm = itemView.findViewById(R.id.btn_alarm);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.alarm_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final Alarm al = list_alarm.get(i);
        int hour = al.getHour();
        int minute = al.getMinute();
        final boolean btnSet = al.isActive();
        String subject = al.getSubject();
        String hour_string = String.valueOf(hour);

        if (minute < 10) {
            hour_string = hour_string + ":" + "0" + String.valueOf(minute);
        } else {
            hour_string = hour_string + ":" + String.valueOf(minute);
        }

        if (hour <= 12) {
            hour_string += " AM";
        } else {
            hour_string += " PM";
        }

        myViewHolder.tvtHour.setText(hour_string);
        myViewHolder.tvtSubject.setText(subject);
        myViewHolder.tglAlarm.setChecked(btnSet);
        myViewHolder.tglAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //udpate alarm db
                    AlarmUtil.setAlarm(context, al);
                    al.setActive(true);
                } else {
                    //udpate alarm db
                    AlarmUtil.cancelAlarm(context, al.getAlarmId());
                    Intent intent = new Intent(Const.ACTION_STOP_ALARM);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.KEY_ALARM, al);
                    intent.putExtra(Const.KEY_BUNDLE, bundle);
                    context.sendBroadcast(intent);
                    al.setActive(false);
                }
                onAlarmCallback.updateAlarm(al);
                notifyItemChanged(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_alarm.size();
    }

}
