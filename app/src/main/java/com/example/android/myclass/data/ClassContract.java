package com.example.android.myclass.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ClassContract {
    public static final String AUTHORITY = "com.example.android.myclass";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_CLASSES = "classes";


    public static final class ClassEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CLASSES).build();
        public static final String TABLE_NAME = "classes";
        public static final String COLUMN_CLASS_NAME = "className";


    }
}
