package com.example.android.myclass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.android.myclass.content.AssignmentsStudentsContent;
import com.example.android.myclass.data.AssignmentItem;
import com.example.android.myclass.data.AssignmentStudentItem;
import com.example.android.myclass.data.AssignmentsContract;

public class StudentDeliveredDetailActivity extends AppCompatActivity {

    Bundle extras;
    AssignmentStudentItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_delivered_detail);

        extras = getIntent().getExtras();
        item = AssignmentsStudentsContent.ITEM_MAP.get(extras.getString("deliveryId"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(item.studentName + "-" + item.assignmentName);

        Cursor c = getContentResolver().query(AssignmentsContract.AssignmentsEntry.CONTENT_URI,
                null,
                AssignmentsContract.AssignmentsEntry._ID + "=?",
                new String[] {Integer.toString(item.assignmentId)},
                null);

        if (c != null && c.getCount() != 0) {
            c.moveToNext();
            String assignmentType = "Regular Assignment";
            int type = c.getInt(c.getColumnIndex(AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_TYPE));

            if (type == AssignmentItem.EXAM) {
                assignmentType = "Exam";
            } else if (type == AssignmentItem.QUIZ) {
                assignmentType = "Quiz";
            }

            String totalGrade =
                    Integer.toString(c.getInt(c.getColumnIndex(AssignmentsContract.AssignmentsEntry.COLUMN_TOTAL_GRADE)));

            TextView details = findViewById(R.id.deliveryDetails);
            details.setText("Assignment Type: " + assignmentType + "\n\n" +
                                "Student Grade: " + item.studentGrade + "/" + totalGrade + "\n\n" +
                                "Comments: " + item.comments);

        }

    }

    public void editButton (View view) {

        Context context = view.getContext();
        Intent intent = new Intent(context, EditDeliveredAssignmentActivity.class);
        intent.putExtra("assignmentDeliveredId", Integer.toString(item.id));

        context.startActivity(intent);
        finish();

    }
}
