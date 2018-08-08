package com.example.android.myclass;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.myclass.data.AssignmentItem;
import com.example.android.myclass.data.AssignmentsContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddAssignmentActivity extends AppCompatActivity {
    Context context = this;
    EditText assignmentName;
    EditText editDate;
    EditText details;
    EditText grade;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd.MM.yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        android.support.v7.widget.Toolbar appBarLayout = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("New Assignment");
        }

        details = findViewById(R.id.details_edit);
        assignmentName = findViewById(R.id.name_edit);
        editDate = (EditText) findViewById(R.id.due_date);
        grade = (EditText) findViewById(R.id.totalGrade);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

    }
    private void updateDate() {
        editDate.setText(sdf.format(myCalendar.getTime()));
    }




    public void saveAssignment(View view) {

        String name = assignmentName.getText().toString().trim();
        String due = editDate.getText().toString().trim();
        String detailStr = details.getText().toString().trim();
        String dateAssign = sdf.format(System.currentTimeMillis());
        String className = "test"; //TODO change to a real class name

        int totalGrade;
        int type = AssignmentItem.REGULAR_ASSIGNMENT;

        RadioGroup typeOptions = (RadioGroup) findViewById(R.id.typeOptions);
        int selectedId = typeOptions.getCheckedRadioButtonId();

        if (selectedId == R.id.quizRadio) {
            type = AssignmentItem.QUIZ;
            totalGrade = Integer.valueOf(grade.getText().toString().trim());
        } else if (selectedId == R.id.examRadio) {
            type = AssignmentItem.EXAM;
            totalGrade = Integer.valueOf(grade.getText().toString().trim());
        } else {
            // fall back to regular assignment option
            totalGrade = 5;
        }

        ContentValues cv = new ContentValues();
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_NAME, name);
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_TOTAL_GRADE, totalGrade);
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_DUE_DATE, due);
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_DETAILS, detailStr);
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_DATE_ASSIGNED, dateAssign);
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_TYPE, type);
        cv.put(AssignmentsContract.AssignmentsEntry.COLUMN_CLASS_NAME, className);

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

    public void DueDateAct(View view) {
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}
