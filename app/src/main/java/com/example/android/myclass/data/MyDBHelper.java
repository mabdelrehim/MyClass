package com.example.android.myclass.data;

import android.content.Context;
import android.database.Cursor;
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


        final String SQL_CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE " +
                AssignmentsContract.AssignmentsEntry.TABLE_NAME + "(" +
                AssignmentsContract.AssignmentsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_NAME + " TEXT NOT NULL," +
                AssignmentsContract.AssignmentsEntry.COLUMN_CLASS_NAME + " TEXT NOT NULL," +
                AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_TYPE + " INTEGER NOT NULL," +
                AssignmentsContract.AssignmentsEntry.COLUMN_DATE_ASSIGNED + " TEXT NOT NULL," +
                AssignmentsContract.AssignmentsEntry.COLUMN_DUE_DATE + " TEXT NOT NULL," +
                AssignmentsContract.AssignmentsEntry.COLUMN_TOTAL_GRADE + " INTEGER NOT NULL," +
                AssignmentsContract.AssignmentsEntry.COLUMN_DETAILS + " TEXT NOT NULL" + ")";

        //TODO add foreign keys to assignment id, student id, student name, and class name
        final String SQL_CREATE_ASSIGNMENTS_STUDENTS_TABLE = "CREATE TABLE " +
                AssignmentStudentContract.AssignmentsStudentsEntry.TABLE_NAME +
                "(" +
                AssignmentStudentContract.AssignmentsStudentsEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMENT_ID +
                " INTEGER NOT NULL," +
                AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_ID +
                " INTEGER NOT NULL," +
                AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMNT_NAME +
                " TEXT NOT NULL," +
                AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_NAME +
                " TEXT NOT NULL," +
                AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_CLASS_NAME +
                " TEXT NOT NULL," +
                AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_GRADE +
                " INTEGER NOT NULL," +
                AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_COMMENTS +
                " TEXT NOT NULL" + ")";

        final String SQL_CREATE_CLASSES_TABLE = "CREATE TABLE " +
                ClassContract.ClassEntry.TABLE_NAME + "(" +
                ClassContract.ClassEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ClassContract.ClassEntry.COLUMN_CLASS_NAME + "TEXT NOT NULL" + ")";

        sqLiteDatabase.execSQL(SQL_CREATE_STUDENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ASSIGNMENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ASSIGNMENTS_STUDENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CLASSES_TABLE);
    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // only refreshes the database you should modify for different behavior

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
                StudentsContract.StudentsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
                AssignmentsContract.AssignmentsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
                AssignmentStudentContract.AssignmentsStudentsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
                ClassContract.ClassEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + StudentsContract.StudentsEntry.TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + StudentsContract.StudentsEntry._ID  + " FROM " + StudentsContract.StudentsEntry.TABLE_NAME +
                " WHERE " + StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void updateAbsence(int id, int oldDays){
        oldDays++;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + StudentsContract.StudentsEntry.TABLE_NAME + " SET " + StudentsContract.StudentsEntry.DAYS_ABSENT +
                " = " + oldDays + " WHERE " + StudentsContract.StudentsEntry._ID + " = " + id;
        db.execSQL(query);
    }
    public void updateAbsence2(int id, int oldDays){
        oldDays--;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + StudentsContract.StudentsEntry.TABLE_NAME + " SET " + StudentsContract.StudentsEntry.DAYS_ABSENT +
                " = " + oldDays + " WHERE " + StudentsContract.StudentsEntry._ID + " = " + id;
        db.execSQL(query);
    }
    /**
     * Returns absenceDays of the students from database
     * @return
     */
    public int getAbsence(int id){
        int daysOfAbsence =0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + StudentsContract.StudentsEntry.DAYS_ABSENT + " FROM " + StudentsContract.StudentsEntry.TABLE_NAME+" WHERE " +
                StudentsContract.StudentsEntry._ID + " = " + id;
        Cursor data = db.rawQuery(query, null);
        if( data != null && data.moveToFirst() ){
            daysOfAbsence = data.getInt(data.getColumnIndex(StudentsContract.StudentsEntry.DAYS_ABSENT));
            data.close();
        }
        // int daysOfAbsence = data.getInt(data.getColumnIndex(StudentsContract.StudentsEntry.DAYS_ABSENT));
        return daysOfAbsence;
    }
}
