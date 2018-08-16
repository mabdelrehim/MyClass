package com.example.android.myclass;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.android.myclass.data.StudentsContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddAssignmentActivity extends AppCompatActivity {
    Context context = this;
    Bundle extras;
    EditText assignmentName;
    EditText editDate;
    EditText details;
    EditText grade;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd.MM.yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    String []emailArray;
    private Intent intent,saveIt;
    Cursor c;
    ArrayList<String> emailList;
    boolean notified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        extras = getIntent().getExtras();

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




    public void EmailAssignment(int totalGrade, String typeString) {

        if (editDate.getText().toString().equalsIgnoreCase("")||
                assignmentName.getText().toString().equalsIgnoreCase("")||
                details.getText().toString().equalsIgnoreCase(""))
        {
            if (editDate.getText().toString().equalsIgnoreCase("")) {
                editDate.setError("This field cannot be empty.");
            }
            if (assignmentName.getText().toString().equalsIgnoreCase("")) {
                assignmentName.setError("This field cannot be empty.");
            }
            if (details.getText().toString().equalsIgnoreCase("")) {
                details.setError("This field cannot be empty");
            }
        }
        else {


            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            emailList = new ArrayList<String>();
            c = getContentResolver().query(StudentsContract.StudentsEntry.CONTENT_URI,
                    null,
                    StudentsContract.StudentsEntry.COLUMN_CLASS_NAME + "=?",
                    new String[]{extras.getString("className")},
                    null);

            if (c != null && c.getCount() != 0) {
                while (c.moveToNext()) {
                    emailList.add(c.getString(c.getColumnIndex(StudentsContract.StudentsEntry.EMAIL)));
                    emailList.add(c.getString(c.getColumnIndex(StudentsContract.StudentsEntry.PARENT_EMAIL)));

                }
                emailArray = emailList.toArray(new String[emailList.size()]);

                c.close();
                intent.putExtra(Intent.EXTRA_EMAIL, emailArray);


                intent.putExtra(Intent.EXTRA_SUBJECT, "New Assignment: "+assignmentName.getText().toString()+"("+typeString+")");
                intent.putExtra(Intent.EXTRA_TEXT, "Due Date: " + editDate.getText().toString() + "\n Total Grade: " +
                        totalGrade + "\n " + "Details: " + details.getText().toString());

                try {
                    startActivity(Intent.createChooser(intent, "Email"));
                    notified = true;
                } catch (android.content.ActivityNotFoundException ex) {
                    notified = false;
                    Toast.makeText(AddAssignmentActivity.this,"Failed to notify students and parents.",
                            Toast.LENGTH_LONG).show();
                }
            }

        }
    }



    public void saveAssignment(View view) {

        String name = assignmentName.getText().toString().trim();
        String due = editDate.getText().toString().trim();
        String detailStr = details.getText().toString().trim();
        String dateAssign = sdf.format(System.currentTimeMillis());
        String className = extras.getString("className");

        int totalGrade = -1;
        int type = AssignmentItem.REGULAR_ASSIGNMENT;
        String assignmentType = "Regular Assignment";
        Boolean inputCorrect = false;

        RadioGroup typeOptions = (RadioGroup) findViewById(R.id.typeOptions);
        int selectedId = typeOptions.getCheckedRadioButtonId();

        if (selectedId == R.id.quizRadio) {
            type = AssignmentItem.QUIZ;
            assignmentType = "Quiz";
            if (grade.getText().toString().equalsIgnoreCase("")) {
                grade.setError("This field cannot be empty for a quiz.");
            } else {
                totalGrade = Integer.valueOf(grade.getText().toString().trim());
                EmailAssignment(totalGrade, assignmentType);
                inputCorrect = true;
            }
        } else if (selectedId == R.id.examRadio) {
            type = AssignmentItem.EXAM;
            assignmentType = "Exam";
            if (grade.getText().toString().equalsIgnoreCase("")) {
                grade.setError("This field cannot be empty for an exam.");
            } else {
                totalGrade = Integer.valueOf(grade.getText().toString().trim());
                EmailAssignment(totalGrade, assignmentType);
                inputCorrect = true;
            }
        } else {
            // fall back to regular assignment option
            totalGrade = 5;
            EmailAssignment(totalGrade, assignmentType);
            inputCorrect = true;
        }


        if (inputCorrect) {
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
        } else {
            Toast.makeText(this, "Incorrect input. All required fields must be filled.",
                    Toast.LENGTH_LONG).show();
        }


    }

    public void dueDateAct(View view) {
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}
