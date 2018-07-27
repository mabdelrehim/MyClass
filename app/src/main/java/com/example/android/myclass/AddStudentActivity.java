package com.example.android.myclass;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.myclass.data.AssignmentItem;
import com.example.android.myclass.data.AssignmentsContract;
import com.example.android.myclass.data.StudentsContract;

public class AddStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
    }

    public void onClickAddTask(View view) {

        /*ContentValues cv = new ContentValues();
        cv.put(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME, "John Snow");
        cv.put(StudentsContract.StudentsEntry.COLUMN_CLASS_NAME, "Winterfell");
        cv.put(StudentsContract.StudentsEntry.DAYS_ABSENT, 5);
        cv.put(StudentsContract.StudentsEntry.EMAIL, "kingInTheNorth@westros.com");
        cv.put(StudentsContract.StudentsEntry.PARENT_EMAIL, "targaryen@westros.com");

        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(StudentsContract.StudentsEntry.CONTENT_URI, cv);
        // Display the URI that's returned with a Toast
        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is
        // complete
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }*/

        ContentValues cv = new ContentValues();

        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_NAME, "hamada");
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_TYPE, AssignmentItem.QUIZ);
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_CLASS_NAME, "hamada land");
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_DATE_ASSIGNED, "2018-12-31");
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_DETAILS, "this is a test input");
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_DUE_DATE, "2019-1-1");
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_TOTAL_GRADE, 20);

        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(AssignmentsContract.AssignmentsEntry.CONTENT_URI, cv);
        // Display the URI that's returned with a Toast
        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is
        // complete
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
        // Finish activity (this returns back to MainActivity)
        finish();

    }
}
