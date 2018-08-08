package com.example.android.myclass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AssignmentDetailsActivity extends AppCompatActivity {

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);
        extras = getIntent().getExtras();

    }

    public void deliveredAssignments(View view) {


        Context context = view.getContext();
        Intent intent = new Intent(context, StudentsDeliveredActivity.class);
        intent.putExtra("itemId", extras.getString("itemId"));

        context.startActivity(intent);
    }
}
