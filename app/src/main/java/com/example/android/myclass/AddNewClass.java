package com.example.android.myclass;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.myclass.data.ClassContract;
import com.example.android.myclass.data.StudentsContract;

public class AddNewClass extends AppCompatActivity {

    EditText EClassname;
    String mClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Class");
        setSupportActionBar(toolbar);
    }

    public void onClickAddClass(View view) {

        EClassname =  findViewById(R.id.class_name_edit);

        if (EClassname.getText().toString().equalsIgnoreCase("")) {
            EClassname.setError("This field cannot be empty.");
        } else {
            mClassName =  EClassname.getText().toString().trim();


            ContentValues cv = new ContentValues();
            cv.put(ClassContract.ClassEntry.COLUMN_CLASS_NAME, mClassName);


            // Insert the content values via a ContentResolver
            Uri uri = getContentResolver().insert(ClassContract.ClassEntry.CONTENT_URI, cv);
            // Display the URI that's returned with a Toast
            // [Hint] Don't forget to call finish() to return to MainActivity after this insert is
            // complete
            if (uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }

            // Finish activity (this returns back to MainActivity)
            finish();
        }
    }
}
