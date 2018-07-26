package com.example.android.myclass.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.android.myclass.data.StudentsContract.StudentsEntry.TABLE_NAME;


//TODO edit content provider to accommodate for new databases
public class MyContentProvider extends ContentProvider {

    public static final int STUDENTS = 100;
    public static final int STUDENT_WITH_ID = 101;
    public static final int CLASSES = 200;
    public static final int CLASS_WITH_ID = 201;
    public static final int ASSIGNMENTS = 300;
    public static final int ASSIGNMENT_WITH_ID = 301;
    public static final int ASSIGNMENTS_STUDENTS = 400;
    public static final int ASSIGNMENT_STUDENT_WITH_ID = 401;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // students directory
        uriMatcher.addURI(StudentsContract.AUTHORITY, StudentsContract.PATH_STUDENTS, STUDENTS);

        // single student item
        uriMatcher.addURI(StudentsContract.AUTHORITY, StudentsContract.PATH_STUDENTS + "/#",
                STUDENT_WITH_ID);

        // classes directory
        uriMatcher.addURI(ClassContract.AUTHORITY, ClassContract.PATH_CLASSES, CLASSES);

        // single class item
        uriMatcher.addURI(ClassContract.AUTHORITY, ClassContract.PATH_CLASSES + "/#",
                CLASS_WITH_ID);

        // assignments directory
        uriMatcher.addURI(AssignmentsContract.AUTHORITY, AssignmentsContract.PATH_ASSIGNMENTS,
                ASSIGNMENTS);

        // single assignment item
        uriMatcher.addURI(AssignmentsContract.AUTHORITY, AssignmentsContract.PATH_ASSIGNMENTS
                + "/#", ASSIGNMENT_WITH_ID);

        // assignments-students directory
        uriMatcher.addURI(AssignmentStudentContract.AUTHORITY,
                AssignmentStudentContract.PATH_ASSIGNMENTS_STUDENTS, ASSIGNMENTS_STUDENTS);

        // single assignment student item
        uriMatcher.addURI(AssignmentStudentContract.AUTHORITY, AssignmentStudentContract.
                PATH_ASSIGNMENTS_STUDENTS + "/#", ASSIGNMENT_STUDENT_WITH_ID);

        return uriMatcher;
    }

    private MyDBHelper mStudentDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mStudentDbHelper = new MyDBHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mStudentDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retVal;
        switch (match) {
            case STUDENTS:
                //query for all students
                retVal = db.query(StudentsContract.StudentsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case ASSIGNMENTS:
                //query for all assignments
                retVal = db.query(AssignmentsContract.AssignmentsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CLASSES:
                //query for all classes
                retVal = db.query(ClassContract.ClassEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case ASSIGNMENTS_STUDENTS:
                retVal = db.query(AssignmentStudentContract.AssignmentsStudentsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Set a notification URI on the Cursor and return that Cursor
        retVal.setNotificationUri(getContext().getContentResolver(), uri);
        return retVal;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case STUDENTS:
                // directory
                return "vnd.android.cursor.dir" + "/" + StudentsContract.AUTHORITY + "/" +
                        StudentsContract.PATH_STUDENTS;
            case STUDENT_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + StudentsContract.AUTHORITY + "/" +
                        StudentsContract.PATH_STUDENTS;
            case ASSIGNMENTS:
                // directory
                return "vnd.android.cursor.dir" + "/" + AssignmentsContract.AUTHORITY + "/" +
                        AssignmentsContract.PATH_ASSIGNMENTS;
            case ASSIGNMENT_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + AssignmentsContract.AUTHORITY + "/" +
                        AssignmentsContract.PATH_ASSIGNMENTS;
            case CLASSES:
                // directory
                return "vnd.android.cursor.dir" + "/" + ClassContract.AUTHORITY + "/" +
                        ClassContract.PATH_CLASSES;
            case CLASS_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + ClassContract.AUTHORITY + "/" +
                        ClassContract.PATH_CLASSES;
            case ASSIGNMENTS_STUDENTS:
                // directory
                return "vnd.android.cursor.dir" + "/" + AssignmentStudentContract.AUTHORITY + "/" +
                        AssignmentStudentContract.PATH_ASSIGNMENTS_STUDENTS;
            case ASSIGNMENT_STUDENT_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + AssignmentStudentContract.AUTHORITY + "/" +
                        AssignmentStudentContract.PATH_ASSIGNMENTS_STUDENTS;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mStudentDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri retVal;
        long id;
        switch (match) {
            case STUDENTS:
                id = db.insert(StudentsContract.StudentsEntry.TABLE_NAME, null,
                        contentValues);
                if (id > 0) {
                    // success
                    retVal = ContentUris.withAppendedId(StudentsContract.StudentsEntry.CONTENT_URI,
                            id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            case ASSIGNMENTS:
                id = db.insert(AssignmentsContract.AssignmentsEntry.TABLE_NAME,
                        null, contentValues);
                if (id > 0) {
                    // success
                    retVal = ContentUris.withAppendedId(AssignmentsContract.AssignmentsEntry
                            .CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            case CLASSES:
                id = db.insert(ClassContract.ClassEntry.TABLE_NAME, null,
                        contentValues);
                if (id > 0) {
                    // success
                    retVal = ContentUris.withAppendedId(ClassContract.ClassEntry.CONTENT_URI,
                            id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            case ASSIGNMENTS_STUDENTS:
                id = db.insert(AssignmentStudentContract.AssignmentsStudentsEntry.TABLE_NAME,
                        null, contentValues);
                if (id > 0) {
                    // success
                    retVal = ContentUris.withAppendedId(AssignmentStudentContract
                            .AssignmentsStudentsEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }

    //TODO: complete the delete and update methods
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mStudentDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int studentsDeleted = 0; // starts as 0
        int classesDeleted = 0; // starts as 0
        int assignmentsDeleted = 0; // starts as 0
        int assignmentsStudentsDeleted = 0; // starts as 0

        String id;

        // Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case STUDENT_WITH_ID:
                // Get the student ID from the URI path
                id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                studentsDeleted = db.delete(StudentsContract.StudentsEntry.TABLE_NAME,
                        "_id=?", new String[]{id});
                break;
            case ASSIGNMENT_WITH_ID:
                // Get the student ID from the URI path
                id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                assignmentsDeleted = db.delete(AssignmentsContract.AssignmentsEntry.TABLE_NAME,
                        "_id=?", new String[]{id});
                break;
            case CLASS_WITH_ID:
                // Get the student ID from the URI path
                id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                classesDeleted = db.delete(ClassContract.ClassEntry.TABLE_NAME,
                        "_id=?", new String[]{id});
                break;
            case ASSIGNMENT_STUDENT_WITH_ID:
                // Get the student ID from the URI path
                id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                assignmentsStudentsDeleted = db.delete(AssignmentStudentContract.
                                AssignmentsStudentsEntry.TABLE_NAME, "_id=?",
                        new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (studentsDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
            return studentsDeleted;
        }
        if (assignmentsDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
            return assignmentsDeleted;
        }
        if (classesDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
            return classesDeleted;
        }
        if (assignmentsStudentsDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
            return assignmentsStudentsDeleted;
        }

        // Return the number of students deleted
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
                      @Nullable String[] strings) {
        //Keep track of if an update occurs
        int studentsUpdated;

        // match code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case STUDENT_WITH_ID:
                //update a single task by getting the id
                String id = uri.getPathSegments().get(1);
                //using selections
                studentsUpdated = mStudentDbHelper.getWritableDatabase().update
                        (TABLE_NAME, contentValues, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (studentsUpdated != 0) {
            //set notifications if a task was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of tasks updated
        return studentsUpdated;
    }
}
