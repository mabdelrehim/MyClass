package com.example.android.myclass.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentsDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;
    public StudentsDBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_STUDENTS_TABLE = "CREATE TABLE " +
                StudentsContract.StudentsEntry.TABLE_NAME + "(" +
                StudentsContract.StudentsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME + " TEXT NOT NULL," +
                StudentsContract.StudentsEntry.COLUMN_CLASS_NAME + " TEXT NOT NULL," +
                StudentsContract.StudentsEntry.EMAIL + " TEXT NOT NULL," +
                StudentsContract.StudentsEntry.PARENT_EMAIL + " TEXT NOT NULL," +
                StudentsContract.StudentsEntry.DAYS_ABSENT + " INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(SQL_CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // only refreshes the database you should modify for different behavior

        sqLiteDatabase.execSQL("Drop Table IF EXISTS " + StudentsContract.StudentsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
