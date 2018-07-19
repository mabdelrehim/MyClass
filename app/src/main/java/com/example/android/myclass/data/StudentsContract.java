package com.example.android.myclass.data;

import android.net.Uri;
import android.provider.BaseColumns;


public class StudentsContract {

    public static final String AUTHORITY = "com.example.android.myclass";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_STUDENTS = "students";


    public static final class StudentsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENTS)
                .build();
        public static final String TABLE_NAME = "students";
        public static final String COLUMN_STUDENT_NAME = "studentName";
        public static final String COLUMN_CLASS_NAME = "className";
        public static final String EMAIL = "email";
        public static final String PARENT_EMAIL = "parentEmail";
        public static final String DAYS_ABSENT = "daysAbsent";


    }
}
