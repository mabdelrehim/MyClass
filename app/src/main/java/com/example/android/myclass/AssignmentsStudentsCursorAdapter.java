package com.example.android.myclass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myclass.content.AssignmentsStudentsContent;
import com.example.android.myclass.data.AssignmentStudentContract;
import com.example.android.myclass.data.AssignmentStudentItem;

public class AssignmentsStudentsCursorAdapter extends
        RecyclerView.Adapter<AssignmentsStudentsCursorAdapter.StudentsViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Cursor tempCursor;
    private Context mContext;
    private boolean mTwoPane;
    private StudentsAssignmentsList mParentActivity;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AssignmentStudentItem item = (AssignmentStudentItem) view.getTag();

            Context context = view.getContext();
            Intent intent = new Intent(context, StudentDeliveredDetailActivity.class);
            intent.putExtra("deliveryId", Integer.toString(item.id));

            context.startActivity(intent);

        }
    };


    /**
     * Constructor for the StudentsCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public AssignmentsStudentsCursorAdapter(Context mContext, StudentsAssignmentsList parent, boolean mTwoPane) {
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

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder holder, int position) {
        // Indices for the _id, studentName, and priority columns
        // id, student name, student id, class name, assignment name, assignment id, grade, comments

        int idIndex = mCursor.getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMENT_ID);
        int studentNameIndex = mCursor.getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_NAME);
        int studentIdIndex = mCursor.getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_ID);
        int classNameIndex = mCursor.getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_CLASS_NAME);
        int assignmentNameIndex = mCursor.getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMENT_NAME);
        int assignmentIdIndex = mCursor.getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_ASSIGNMENT_ID);
        int gradeIndex = mCursor.getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_STUDENT_GRADE);
        int commentsIndex = mCursor.getColumnIndex(AssignmentStudentContract.AssignmentsStudentsEntry.COLUMN_COMMENTS);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        AssignmentStudentItem item = new AssignmentStudentItem(mCursor.getInt(idIndex),
                mCursor.getString(studentNameIndex),
                mCursor.getInt(studentIdIndex),
                mCursor.getString(classNameIndex),
                mCursor.getInt(assignmentIdIndex),
                mCursor.getInt(gradeIndex),
                mCursor.getString(commentsIndex),
                mCursor.getString(assignmentNameIndex));
        AssignmentsStudentsContent.ITEM_MAP.put(Integer.toString(item.id), item);



        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String assignmentName = mCursor.getString(assignmentNameIndex);

        //Set values
        holder.itemView.setTag(item);
        holder.studentNameView.setText(assignmentName);
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

