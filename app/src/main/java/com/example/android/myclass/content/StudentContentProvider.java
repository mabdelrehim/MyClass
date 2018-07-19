package com.example.android.myclass.content;

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
                        projection, selection, selectionArgs, null, null,
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
        return null;
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
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
                      @Nullable String[] strings) {
        return 0;
    }
}
