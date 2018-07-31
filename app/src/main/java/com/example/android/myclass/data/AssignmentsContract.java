package com.example.android.myclass.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class AssignmentsContract {
    public static final String AUTHORITY = "com.example.android.myclass";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_ASSIGNMENTS = "assignments";


    public static final class AssignmentsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ASSIGNMENTS).build();
        public static final String TABLE_NAME = "assignments";
        public static final String COLUMN_ASSIGNMENT_NAME = "assignmentName";
        public static final String COLUMN_ASSIGNMENT_TYPE = "assignmentType";
        public static final String COLUMN_TOTAL_GRADE = "totalGrade";
        public static final String COLUMN_DETAILS = "details";
        public static final String COLUMN_DATE_ASSIGNED = "dateAssigned";
        public static final String COLUMN_DUE_DATE = "dueDate";
        public static final String COLUMN_CLASS_NAME = "className";

    }
}
