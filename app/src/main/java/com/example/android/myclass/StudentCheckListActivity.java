package com.example.android.myclass;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.myclass.data.MyDBHelper;
import com.example.android.myclass.data.Student;
import com.example.android.myclass.data.StudentItem;
import com.example.android.myclass.data.StudentsContract;

import java.util.ArrayList;

public class StudentCheckListActivity extends AppCompatActivity {

    private static final String TAG = "students_check_list";
    int i ;
    Student std;
    MyDBHelper mDatabaseHelper;
    ArrayList<StudentItem> listData;
    ArrayList<Integer> absenceDays;
    Bundle extras;
    Cursor mCursor ;

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_check_list);
        extras = getIntent().getExtras();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mDatabaseHelper = new MyDBHelper(this);

        populateListView();

    }

    private void populateListView() {





        //get the data and append to a list
        mCursor = mDatabaseHelper.getData(extras.getString("className"));

        int idIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry._ID);
        int nameIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME);
        int classNameIndex = mCursor.getColumnIndex
                (StudentsContract.StudentsEntry.COLUMN_CLASS_NAME);
        int emailIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry.EMAIL);
        int parentEmailIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry.PARENT_EMAIL);
        int daysAbsentIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry.DAYS_ABSENT);

        listData = new ArrayList<>();
        while (mCursor.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(new StudentItem(mCursor.getInt(idIndex),
                    mCursor.getString(nameIndex),
                    mCursor.getString(classNameIndex),
                    mCursor.getString(emailIndex),
                    mCursor.getString(parentEmailIndex),
                    mCursor.getInt(daysAbsentIndex)));
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //create the list adapter and set the adapter
        StudentAdapter adapter = new StudentAdapter(this, listData);
        mRecyclerView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
//        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = adapterView.getItemAtPosition(i).toString();
//
//                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with that name
//                int itemID = -1;
//                while (data.moveToNext()) {
//                    itemID = data.getInt(0);
//                }
//
//            }
//        });

    }

    public void saveToDB(View view) {
//        i=0;
//        Log.d(TAG, "SSAAAAAVVVEEE");
//        absenceDays = new ArrayList<>();
//        Cursor data = mDatabaseHelper.getAbsence(view.toString());
//
//        while (data.moveToNext()) {
//            //get the value from the database in column 1
//            //then add it to the ArrayList
//            absenceDays.add(data.getInt(0));
//            Log.e(TAG, "absence day : " + data.getInt(0));
//
//        }
//
//        Log.d(TAG, "onClick: hopa tito mambo");
//        if(listData.get(0).getmIsChecked() == false)
//        mDatabaseHelper.updateAbsence(listData.get(0).getmName(), 3);
//
//        else
//            mDatabaseHelper.updateAbsence(listData.get(0).getmName(), 5);

//        Iterator<Student> iter = listData.iterator();
//
//        while (iter.hasNext()) {
//
//            if (listData.get(0).getmIsChecked() == false) {
//                int old = (int) absenceDays.get(0);
//                Log.d(TAG, "onClick: " + listData.get(0).getmName() + " dayss: " + old);
//                mDatabaseHelper.updateAbsence(listData.get(0).getmName(), old);
//            }
//            i++;
//        }


        Intent intent = new Intent(this, ClassOptionsActivity.class);
        startActivity(intent);
    }
}
