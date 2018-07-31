package com.example.android.myclass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myclass.content.StudentsContent;
import com.example.android.myclass.data.StudentItem;
import com.example.android.myclass.data.StudentsContract;

public class StudentsCursorAdapter
        extends RecyclerView.Adapter<StudentsCursorAdapter.StudentsViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    private boolean mTwoPane;
    private StudentListActivity mParentActivity;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            StudentItem item = (StudentItem) view.getTag();
            /*if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(StudentDetailFragment.ARG_ITEM_ID, Integer.toString(item.id));
                StudentDetailFragment fragment = new StudentDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.student_detail_container, fragment)
                        .commit();
            }*/
            Context context = view.getContext();
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra(StudentDetailFragment.ARG_ITEM_ID, Integer.toString(item.id));

            context.startActivity(intent);

        }
    };


    /**
     * Constructor for the StudentsCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public StudentsCursorAdapter(Context mContext, StudentListActivity parent, boolean mTwoPane) {
        this.mContext = mContext;
        this.mParentActivity = parent;
        this.mTwoPane = mTwoPane;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public StudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.numbered_list_content, parent, false);

        return new StudentsViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(StudentsViewHolder holder, int position) {

        // Indices for the _id, studentName, and priority columns
        int idIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry._ID);
        int nameIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry.COLUMN_STUDENT_NAME);
        int classNameIndex = mCursor.getColumnIndex
                (StudentsContract.StudentsEntry.COLUMN_CLASS_NAME);
        int emailIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry.EMAIL);
        int parentEmailIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry.PARENT_EMAIL);
        int daysAbsentIndex = mCursor.getColumnIndex(StudentsContract.StudentsEntry.DAYS_ABSENT);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        StudentItem item = new StudentItem(mCursor.getInt(idIndex),
                mCursor.getString(nameIndex),
                mCursor.getString(classNameIndex),
                mCursor.getString(emailIndex),
                mCursor.getString(parentEmailIndex),
                mCursor.getInt(daysAbsentIndex));
        StudentsContent.ITEM_MAP.put(Integer.toString(item.id), item);



        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String studentName = mCursor.getString(nameIndex);

        //Set values
        holder.itemView.setTag(item);
        holder.studentNameView.setText(studentName);
        holder.numView.setText(String.valueOf(position + 1));

        holder.itemView.setOnClickListener(mOnClickListener);

    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    // Inner class for creating ViewHolders
    class StudentsViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView studentNameView;
        TextView numView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public StudentsViewHolder(View itemView) {
            super(itemView);

            studentNameView = (TextView) itemView.findViewById(R.id.content);
            numView = (TextView) itemView.findViewById(R.id.id_text);
        }
    }

}
