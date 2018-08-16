package com.example.android.myclass;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myclass.data.StudentsContract;

import org.w3c.dom.Text;

public class AddStudentActivity extends AppCompatActivity {


    EditText Ename;
    EditText ESEmail;
    EditText EPEmail;
    TextView EClass;
    String mName;
    String mClass;
    String mEmail;
    String mPEmail;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Student");

        Ename = (EditText) findViewById(R.id.namefield_edit);
        ESEmail = (EditText) findViewById(R.id.emailfield_edit);
        EPEmail = (EditText) findViewById(R.id.parentfield_edit);
        EClass = (TextView) findViewById(R.id.classfield_edit);

        extras = getIntent().getExtras();
        EClass.setText("Class: " + extras.getString("className"));
    }

    public void onClickAddTask(View view) {



        mName = (String) Ename.getText().toString().trim();
        mEmail = (String) ESEmail.getText().toString().trim();
        mPEmail = (String) EPEmail.getText().toString().trim();
        mClass = extras.getString("className");

        ContentValues cv = new ContentValues();
        cv.put(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME, mName);
        cv.put(StudentsContract.StudentsEntry.COLUMN_CLASS_NAME, mClass);
        cv.put(StudentsContract.StudentsEntry.DAYS_ABSENT, 0);
        cv.put(StudentsContract.StudentsEntry.EMAIL, mEmail);
        cv.put(StudentsContract.StudentsEntry.PARENT_EMAIL, mPEmail);

        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(StudentsContract.StudentsEntry.CONTENT_URI, cv);
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
