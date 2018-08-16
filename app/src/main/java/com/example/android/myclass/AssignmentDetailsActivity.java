package com.example.android.myclass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.android.myclass.content.AssignmentsContent;
import com.example.android.myclass.data.AssignmentItem;

public class AssignmentDetailsActivity extends AppCompatActivity {

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);

        extras = getIntent().getExtras();

        AssignmentItem item = AssignmentsContent.ITEM_MAP.get(extras.getString("assignmentId"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(extras.getString("assignmentName"));
        setSupportActionBar(toolbar);

        TextView tv = findViewById(R.id.assign_details);
        tv.setText("Date Assigned: " + item.dateAssigned + "\n\n" +
                    "Due Date: " + item.dueDate + "\n\n" +
                    "Details: " + item.details);

    }

    public void deliveredAssignments(View view) {


        Context context = view.getContext();
        Intent intent = new Intent(context, StudentsDeliveredActivity.class);
        intent.putExtra("assignmentId", extras.getString("assignmentId"));

        context.startActivity(intent);
    }
}
