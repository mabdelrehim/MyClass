package com.example.android.myclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClassOptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_options);
        Button attendance = (Button) findViewById(R.id.Attendance_button);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ClassOptionsActivity.this, TakeAttendanceActivity.class);
                startActivity(i);
            }
        });
        Button viewStds = (Button) findViewById(R.id.viewStudents_button);
        viewStds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ClassOptionsActivity.this,StudentListActivity.class);
                startActivity(i);
            }
        });
    }
}
