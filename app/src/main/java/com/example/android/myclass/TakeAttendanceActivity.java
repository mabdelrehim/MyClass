package com.example.android.myclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TakeAttendanceActivity extends AppCompatActivity {


    com.applandeo.materialcalendarview.CalendarView calendarView;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        extras = getIntent().getExtras();

        calendarView = findViewById(R.id.calendarView);
        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.background_color_circle_selector));

        calendarView.setEvents(events);


        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Intent intent = new Intent(TakeAttendanceActivity.this,
                        StudentCheckListActivity.class);
                intent.putExtra("className", extras.getString("className"));
                startActivity(intent);
            }
        });
    }
}

