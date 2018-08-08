package com.example.android.myclass.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class AssignmentStudentContract {

    public static final String AUTHORITY = "com.example.android.myclass";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_ASSIGNMENTS_STUDENTS = "assignmentsStudents";


    public static final class AssignmentsStudentsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(PATH_ASSIGNMENTS_STUDENTS).build();
        public static final String TABLE_NAME = "assignmentsStudents";
        public static final String COLUMN_STUDENT_NAME = "studentName";
        public static final String COLUMN_STUDENT_ID = "studentID";
        public static final String COLUMN_CLASS_NAME = "className";
        public static final String COLUMN_ASSIGNMENT_ID = "assignmentID";
        public static final String COLUMN_ASSIGNMENT_NAME = "assignmentName";
        public static final String COLUMN_STUDENT_GRADE = "studentGrade";
        public static final String COLUMN_COMMENTS = "comments";


    }

}
