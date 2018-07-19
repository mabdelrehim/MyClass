package com.example.android.myclass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myclass.content.StudentContent;
import com.example.android.myclass.data.StudentItem;
import com.example.android.myclass.data.StudentsContract;
import com.example.android.myclass.data.StudentsDBHelper;
import com.example.android.myclass.data.TestUtil;

import java.util.List;

/**
 * An activity representing a list of Students. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StudentDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StudentListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.student_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        StudentsDBHelper dbHelper = new StudentsDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        TestUtil.insertFakeData(db);

        Cursor cursor = db.query(StudentsContract.StudentsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        try {
            while (cursor.moveToNext()) {
                String studentName = cursor.getString(cursor.getColumnIndex(StudentsContract
                        .StudentsEntry.COLUMN_STUDENT_NAME));
                String studentClass = cursor.getString(cursor.getColumnIndex(StudentsContract
                        .StudentsEntry.COLUMN_CLASS_NAME));
                String email = cursor.getString(cursor.getColumnIndex(StudentsContract
                        .StudentsEntry.EMAIL));
                String parentEmail = cursor.getString(cursor.getColumnIndex(StudentsContract
                        .StudentsEntry.PARENT_EMAIL));
                int daysAbsent = cursor.getInt(cursor.getColumnIndex(StudentsContract
                        .StudentsEntry.DAYS_ABSENT));
                int id = cursor.getInt(cursor.getColumnIndex(StudentsContract
                        .StudentsEntry._ID));


                StudentItem item = new StudentItem(Integer.toString(id), studentName, studentClass,
                        email, parentEmail, Integer.toString(daysAbsent));
                StudentContent.addItem(item);
            }
        } finally {
            cursor.close();
        }


        View recyclerView = findViewById(R.id.student_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, StudentContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final StudentListActivity mParentActivity;
        private final List<StudentItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentItem item = (StudentItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(StudentDetailFragment.ARG_ITEM_ID, item.id);
                    StudentDetailFragment fragment = new StudentDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.student_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StudentDetailActivity.class);
                    intent.putExtra(StudentDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(StudentListActivity parent,
                                      List<StudentItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.student_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).studentName);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
