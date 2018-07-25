package com.example.android.myclass.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//TODO init create tables strings

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myAppDatabase.db";
    private static final int DATABASE_VERSION = 1;
    public MyDBHelper(Context context) {
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


        final String SQL_CREATE_ASSIGNMENTS_TABLE;
        final String SQL_CREATE_ASSIGNMENTS_STUDENTS_TABLE;
        final String SQL_CREATE_CLASSES_TABLE;

        sqLiteDatabase.execSQL(SQL_CREATE_STUDENTS_TABLE);
        /*sqLiteDatabase.execSQL(SQL_CREATE_ASSIGNMENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ASSIGNMENTS_STUDENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CLASSES_TABLE);*/
    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // only refreshes the database you should modify for different behavior

        sqLiteDatabase.execSQL("Drop Table IF EXISTS " + StudentsContract.StudentsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
