package com.example.android.myclass;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.myclass.content.AssignmentsContent;
import com.example.android.myclass.data.AssignmentItem;
import com.example.android.myclass.data.AssignmentStudentContract;
import com.example.android.myclass.data.StudentsContract;

public class AddStudentDeliveredActivity extends Activity {

    Bundle extras;
    EditText name;
    EditText grade;
    EditText comments;
    AssignmentItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_delivered);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add a student");

        name = findViewById(R.id.stdName);
        grade = findViewById(R.id.grade);
        comments = findViewById(R.id.comments);

        extras = getIntent().getExtras();
        item = AssignmentsContent.ITEM_MAP.get(extras.getString("assignmentId"));
        String assignmentId = Integer.toString(item.id);
        String totalGrade = Integer.toString(item.totalGrade);

        TextView tv = findViewById(R.id.textView);
        tv.setText("This assignment is out of " + totalGrade);


    }

    public void saveAssignStd(View view) {

        if (name.getText().toString().trim().equalsIgnoreCase("") ||
                grade.getText().toString().trim().equalsIgnoreCase("")||
                comments.getText().toString().trim().equalsIgnoreCase("")) {

            if (name.getText().toString().trim().equalsIgnoreCase(""))
                name.setError("This field cannot be empty");
            if (grade.getText().toString().trim().equalsIgnoreCase(""))
                grade.setError("This field cannot be empty");
            if (comments.getText().toString().trim().equalsIgnoreCase(""))
                comments.setError("This field cannot be empty. Write 'None' if there are no comments.");

        } else {

            String studentName = name.getText().toString().trim();
            String selection = StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME + "=?";
            String[] selectionArgs = {studentName};
            Cursor cursor = getContentResolver().query(StudentsContract.StudentsEntry.CONTENT_URI,
                    null,
                    selection,
                    selectionArgs,
                    null);
            if (cursor != null && cursor.getCount() > 0) {
                // there is a student with that name in the class
                cursor.moveToNext();
                int stdidindex = cursor.getColumnIndex(StudentsContract.StudentsEntry._ID);
                int stdid = cursor.getInt(stdidindex);
                ContentValues cv = new ContentValues();
                cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_NAME, studentName);
                cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMENT_ID, item.id);
                cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMENT_NAME, item.assignmentName);
                cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_CLASS_NAME, item.className);
                cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_COMMENTS, comments.getText().toString().trim());
                cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_GRADE, grade.getText().toString().trim());
                cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_ID, stdid);

                Uri uri = getContentResolver().insert(AssignmentStudentContract.AssignmentsStudentsEntry.CONTENT_URI, cv);
                finish();

            } else {
                name.setError("Can't find a student with that name");
            }

        }




    }

}
