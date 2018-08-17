package com.example.android.myclass;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.example.android.myclass.content.StudentsContent;
import com.example.android.myclass.data.AssignmentStudentContract;
import com.example.android.myclass.data.AssignmentsContract;
import com.example.android.myclass.data.StudentItem;

/**
 * An activity representing a single Student detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StudentListActivity}.
 */
public class StudentDetailActivity extends AppCompatActivity {

    Bundle extras;
    String email;
    StudentItem studentItem;
    Intent emailIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);*/


        extras = getIntent().getExtras();

        studentItem= StudentsContent.ITEM_MAP.get(extras.getString(StudentDetailFragment.ARG_ITEM_ID));
        String assignmentSelection = AssignmentsContract.AssignmentsEntry.COLUMN_CLASS_NAME + "=?";
        String[] assignmentSelectionArgs = {studentItem.studentClass};
        Cursor assignments = getContentResolver().query(AssignmentsContract.AssignmentsEntry.CONTENT_URI,
                null,
                assignmentSelection,
                assignmentSelectionArgs,
                null);

        String assignStdSelection = AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_ID + "=?";
        String[] assignStdSelectionArgs = {Integer.toString(studentItem.id)};
        Cursor assignStds = getContentResolver().query(AssignmentStudentContract.AssignmentsStudentsEntry.CONTENT_URI,
                null,
                assignStdSelection,
                assignStdSelectionArgs,
                null);

        String collectedGrades;
        int collected = 0;
        int total = 0;

        if ((assignments != null && assignments.getCount() != 0) &&
                (assignStds != null && assignStds.getCount() != 0)) {
            while (assignments.moveToNext()) {
                total += assignments.getInt(assignments
                        .getColumnIndex(AssignmentsContract.AssignmentsEntry.COLUMN_TOTAL_GRADE));
            }
            while (assignStds.moveToNext()) {
                collected += assignStds.getInt(assignStds
                        .getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_GRADE));
            }
            collectedGrades = "Collected grades: " + collected + " out of " + total;
        } else {
            collectedGrades = "No grades yet";
        }


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(StudentDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(StudentDetailFragment.ARG_ITEM_ID));
            arguments.putString("collected", collectedGrades);
            StudentDetailFragment fragment = new StudentDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.assignment_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, StudentListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickViewAssign(View view) {
        Context c = view.getContext();
        Intent viewAssignmentsIntent = new Intent(StudentDetailActivity.this,
                StudentsAssignmentsList.class);
        studentItem= StudentsContent.ITEM_MAP.get(extras.getString(StudentDetailFragment.ARG_ITEM_ID));
        viewAssignmentsIntent.putExtra("studentId", Integer.toString(studentItem.id));
        c.startActivity(viewAssignmentsIntent);
    }

    public void EmailActivity(View view) {
        CharSequence options[] = new CharSequence[] {"Parent", "Student"};
        studentItem= StudentsContent.ITEM_MAP.get(extras.getString(StudentDetailFragment.ARG_ITEM_ID));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send email to:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        email=studentItem.parentEmail;
                        break;
                    case 1:
                        email=studentItem.email;
                        break;
                    default:
                        break;
                }

                emailIntent= new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});




                try {
                    startActivity(Intent.createChooser(emailIntent, "Email"));
                } catch (android.content.ActivityNotFoundException ex) {

                }

            }

        });
        builder.show();

    }
}
