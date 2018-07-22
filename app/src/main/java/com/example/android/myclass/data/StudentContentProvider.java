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

import com.example.android.myclass.data.StudentsContract;
import com.example.android.myclass.data.StudentsDBHelper;

import static com.example.android.myclass.data.StudentsContract.StudentsEntry.TABLE_NAME;

public class StudentContentProvider extends ContentProvider {

    public static final int STUDENTS = 100;
    public static final int STUDENT_WITH_ID = 101;
    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // students directory
        uriMatcher.addURI(StudentsContract.AUTHORITY, StudentsContract.PATH_STUDENTS, STUDENTS);

        // single student item
        uriMatcher.addURI(StudentsContract.AUTHORITY, StudentsContract.PATH_STUDENTS + "/#",
                STUDENT_WITH_ID);

        return uriMatcher;
    }

    private StudentsDBHelper mStudentDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mStudentDbHelper = new StudentsDBHelper(context);

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
        switch (match) {
            case STUDENTS:
                long id = db.insert(StudentsContract.StudentsEntry.TABLE_NAME, null,
                        contentValues);
                if (id > 0) {
                    // success
                    retVal = ContentUris.withAppendedId(StudentsContract.StudentsEntry.CONTENT_URI,
                            id);
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

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mStudentDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int studentsDeleted; // starts as 0

        // Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case STUDENT_WITH_ID:
                // Get the student ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                studentsDeleted = db.delete(StudentsContract.StudentsEntry.TABLE_NAME,
                        "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (studentsDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of students deleted
        return studentsDeleted;
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
