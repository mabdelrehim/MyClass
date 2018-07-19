package com.example.android.myclass.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME, "John Snow");
        cv.put(StudentsContract.StudentsEntry.COLUMN_CLASS_NAME, "Winterfell");
        cv.put(StudentsContract.StudentsEntry.DAYS_ABSENT, 5);
        cv.put(StudentsContract.StudentsEntry.EMAIL, "kingInTheNorth@westros.com");
        cv.put(StudentsContract.StudentsEntry.PARENT_EMAIL, "targaryen@westros.com");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME, "Batman");
        cv.put(StudentsContract.StudentsEntry.COLUMN_CLASS_NAME, "Gotham");
        cv.put(StudentsContract.StudentsEntry.DAYS_ABSENT, 0);
        cv.put(StudentsContract.StudentsEntry.EMAIL, "batman@gotham.com");
        cv.put(StudentsContract.StudentsEntry.PARENT_EMAIL, "orphan@gotham.com");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME, "Joker");
        cv.put(StudentsContract.StudentsEntry.COLUMN_CLASS_NAME, "Gotham");
        cv.put(StudentsContract.StudentsEntry.DAYS_ABSENT, 3);
        cv.put(StudentsContract.StudentsEntry.EMAIL, "iHateBatman@gotham.com");
        cv.put(StudentsContract.StudentsEntry.PARENT_EMAIL, "jokersmom@gotham.com");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME, "Arya Stark");
        cv.put(StudentsContract.StudentsEntry.COLUMN_CLASS_NAME, "Winterfell");
        cv.put(StudentsContract.StudentsEntry.DAYS_ABSENT, 5);
        cv.put(StudentsContract.StudentsEntry.EMAIL, "angry@westros.com");
        cv.put(StudentsContract.StudentsEntry.PARENT_EMAIL, "killed@westros.com");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME, "Spider Man");
        cv.put(StudentsContract.StudentsEntry.COLUMN_CLASS_NAME, "NY");
        cv.put(StudentsContract.StudentsEntry.DAYS_ABSENT, 4);
        cv.put(StudentsContract.StudentsEntry.EMAIL, "spider@marvel.com");
        cv.put(StudentsContract.StudentsEntry.PARENT_EMAIL, "killed@marvel.com");
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (StudentsContract.StudentsEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(StudentsContract.StudentsEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }

}
