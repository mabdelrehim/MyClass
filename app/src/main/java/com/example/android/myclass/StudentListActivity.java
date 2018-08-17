package com.example.android.myclass;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.myclass.data.AssignmentStudentContract;
import com.example.android.myclass.data.StudentItem;
import com.example.android.myclass.data.StudentsContract;

/**
 * An activity representing a list of Students. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StudentDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StudentListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    // Constants for logging and referring to a unique loader
    private static final String TAG = StudentListActivity.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;

    // Member variables for the adapter and RecyclerView
    private StudentsCursorAdapter mAdapter;
    private boolean mTwoPane;
    RecyclerView mRecyclerView;
    Bundle extras;
    Context thisContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Students");
        extras = getIntent().getExtras();
        thisContext = this;

        //commented code for larger screen implementation
        //deprecated
        /*if (findViewById(R.id.student_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }*/

        // Set the RecyclerView to its corresponding view
        mRecyclerView = (RecyclerView) findViewById(R.id.student_list);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new StudentsCursorAdapter(this, this, mTwoPane);
        mRecyclerView.setAdapter(mAdapter);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {


                final StudentItem item = (StudentItem) viewHolder.itemView.getTag();

                AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);
                builder.setTitle("Delete!");
                builder.setMessage("Are you sure you want to delete this student?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Build appropriate uri with String row id appended
                        String stringId = Integer.toString(item.id);
                        Uri uri = StudentsContract.StudentsEntry.CONTENT_URI;
                        uri = uri.buildUpon().appendPath(stringId).build();

                        // COMPLETED (2) Delete a single row of data using a ContentResolver
                        getContentResolver().delete(uri, null, null);

                        String selection =
                                AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_ID + "=?";
                        String[] selectionArgs = {stringId};

                        getContentResolver().delete(AssignmentStudentContract.AssignmentsStudentsEntry.CONTENT_URI,
                                selection,
                                selectionArgs);

                        // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
                        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null,
                                StudentListActivity.this);

                        Toast.makeText(getApplicationContext(), "Student Deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();


            }
        }).attachToRecyclerView(mRecyclerView);

        /*
         Set the Floating Action Button (FAB) to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code here
                // Create a new intent to start an AddTaskActivity
                Intent addStudentIntent = new Intent(StudentListActivity.this,
                        AddStudentActivity.class);
                addStudentIntent.putExtra("className", extras.getString("className"));
                startActivity(addStudentIntent);
            }
        });

        /*
         Ensure a loader is initialized and active. If the loader doesn't already exist, one is
         created, otherwise the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
    }


    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an AddTaskActivity,
     * so this restarts the loader to re-query the underlying data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }


    /**
     * Instantiates and returns a new AsyncTaskLoader with the given ID.
     * This loader will return task data as a Cursor or null if an error occurs.
     *
     * Implements the required callbacks to take care of loading data at all stages of loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(StudentsContract.StudentsEntry.CONTENT_URI,
                            null,
                            StudentsContract.StudentsEntry.COLUMN_CLASS_NAME + "=?",
                            new String[] {extras.getString("className")},
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }


    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        mAdapter.swapCursor(data);
    }


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
