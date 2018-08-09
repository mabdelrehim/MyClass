package com.example.android.myclass;

import android.content.ContentValues;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.android.myclass.content.AssignmentsStudentsContent;
import com.example.android.myclass.data.AssignmentStudentContract;
import com.example.android.myclass.data.AssignmentStudentItem;

public class EditDeliveredAssignmentActivity extends Activity {

    Bundle extras;

    EditText newGradeE;
    EditText newCommentE;
    AssignmentStudentItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivered_assignment);

        extras = getIntent().getExtras();
        newGradeE = findViewById(R.id.gradeEdit);
        newCommentE = findViewById(R.id.commentsEdit);

        item = AssignmentsStudentsContent.ITEM_MAP.get
                (extras.getString("assignmentDeliveredId"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit " + item.studentName + "-" + item.assignmentName);
    }

    public void updateDeliveredAssignment(View view) {
        if(TextUtils.isEmpty(newGradeE.getText().toString().trim())) {
            newGradeE.setError("This field cannot be empty");
        } else if (TextUtils.isEmpty(newCommentE.getText().toString().trim())) {
            newCommentE.setError("This field cannot be empty");
        } else {
            ContentValues cv = new ContentValues();
            cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_ID, item.studentId);
            cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_GRADE, String.valueOf(newGradeE.getText().toString().trim()));
            cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_COMMENTS, newCommentE.getText().toString().trim());
            cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_CLASS_NAME, item.className);
            cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMENT_ID, item.assignmentId);
            cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_NAME, item.studentName);
            cv.put(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMENT_NAME, item.assignmentName);
            cv.put(AssignmentStudentContract.AssignmentsStudentsEntry._ID, item.id);

            int updateCount = getContentResolver().update(AssignmentStudentContract.AssignmentsStudentsEntry.CONTENT_URI,
                    cv, AssignmentStudentContract.AssignmentsStudentsEntry._ID + "=?",
                    new String[] {Integer.toString(item.id)});
            finish();
        }
    }

}
