package com.example.android.myclass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AssignmentDetailsActivity extends AppCompatActivity {

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);

        extras = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(extras.getString("assignmentName"));

    }

    public void deliveredAssignments(View view) {


        Context context = view.getContext();
        Intent intent = new Intent(context, StudentsDeliveredActivity.class);
        intent.putExtra("assignmentId", extras.getString("assignmentId"));

        context.startActivity(intent);
    }
}
