package com.example.android.myclass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myclass.content.ClassesContent;
import com.example.android.myclass.content.StudentsContent;
import com.example.android.myclass.data.ClassContract;
import com.example.android.myclass.data.ClassItem;
import com.example.android.myclass.data.StudentItem;
import com.example.android.myclass.data.StudentsContract;

public class ClassesCursorAdapter
        extends RecyclerView.Adapter<ClassesCursorAdapter.ClassesViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    private boolean mTwoPane;
    // private StudentListActivity mParentActivity;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ClassItem item = (ClassItem) view.getTag();
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
            Intent intent = new Intent(context, ClassOptionsActivity.class);
            intent.putExtra("Class Name", item.className);

            context.startActivity(intent);

        }
    };


    /**
     * Constructor for the StudentsCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public ClassesCursorAdapter(Context mContext, boolean mTwoPane) {
        this.mContext = mContext;

        this.mTwoPane = mTwoPane;
    }



    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public ClassesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.numbered_list_content, parent, false);

        return new ClassesViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(ClassesViewHolder holder, int position) {

        // Indices for the _id, studentName, and priority columns
        int idIndex = mCursor.getColumnIndex(ClassContract.ClassEntry._ID);
        int classNameIndex = mCursor.getColumnIndex
                (ClassContract.ClassEntry.COLUMN_CLASS_NAME);


        mCursor.moveToPosition(position); // get to the right location in the cursor

        ClassItem item = new ClassItem(mCursor.getInt(idIndex),
                mCursor.getString(classNameIndex)
        );
        ClassesContent.ITEM_MAP.put(Integer.toString(item.id), item);



        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String ClassName = mCursor.getString(classNameIndex);

        //Set values
        holder.itemView.setTag(item);
        holder.ClassNameView.setText(ClassName);
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
    class ClassesViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView ClassNameView;
        TextView numView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public ClassesViewHolder(View itemView) {
            super(itemView);

            ClassNameView = (TextView) itemView.findViewById(R.id.content);
            numView = (TextView) itemView.findViewById(R.id.id_text);
        }
    }

}
