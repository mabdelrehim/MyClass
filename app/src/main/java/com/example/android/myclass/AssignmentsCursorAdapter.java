package com.example.android.myclass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myclass.content.AssignmentsContent;
import com.example.android.myclass.data.AssignmentItem;
import com.example.android.myclass.data.AssignmentsContract;


public class AssignmentsCursorAdapter
        extends RecyclerView.Adapter<AssignmentsCursorAdapter.AssignmentsViewHolder>{

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    private boolean mTwoPane;
    private AssignmentListActivity mParentActivity;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AssignmentItem item = (AssignmentItem) view.getTag();
            // TODO: when a user clicks on a specific assignment

            Intent intent = new Intent(mContext, AssignmentDetailsActivity.class);
            intent.putExtra("itemId", Integer.toString(item.id));

            mContext.startActivity(intent);
            /*Context context = view.getContext();
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra(StudentDetailFragment.ARG_ITEM_ID, Integer.toString(item.id));

            context.startActivity(intent);*/

        }
    };


    /**
     * Constructor for the StudentsCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public AssignmentsCursorAdapter(Context mContext, AssignmentListActivity parent, boolean mTwoPane) {
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
    public AssignmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.numbered_list_content, parent, false);

        return new AssignmentsViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(AssignmentsViewHolder holder, int position) {

        // Indices for the _id, studentName, and priority columns
        int idIndex = mCursor.getColumnIndex(AssignmentsContract.AssignmentsEntry._ID);
        int assignmentNameIndex = mCursor.getColumnIndex
                (AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_NAME);
        int classNameIndex = mCursor.getColumnIndex
                (AssignmentsContract.AssignmentsEntry.COLUMN_CLASS_NAME);
        int assignmentTypeIndex = mCursor.getColumnIndex
                (AssignmentsContract.AssignmentsEntry.COLUMN_ASSIGNMENT_TYPE);
        int dateAssignedTypeIndex = mCursor.getColumnIndex
                (AssignmentsContract.AssignmentsEntry.COLUMN_DATE_ASSIGNED);
        int dueDateIndex = mCursor.getColumnIndex
                (AssignmentsContract.AssignmentsEntry.COLUMN_DUE_DATE);
        int totalGradeIndex = mCursor.getColumnIndex
                (AssignmentsContract.AssignmentsEntry.COLUMN_TOTAL_GRADE);
        int detailsIndex = mCursor.getColumnIndex
                (AssignmentsContract.AssignmentsEntry.COLUMN_DETAILS);


        mCursor.moveToPosition(position); // get to the right location in the cursor

        AssignmentItem item = new AssignmentItem(mCursor.getInt(idIndex),
                mCursor.getString(assignmentNameIndex),
                mCursor.getInt(assignmentTypeIndex),
                mCursor.getInt(totalGradeIndex),
                mCursor.getString(detailsIndex),
                mCursor.getString(dateAssignedTypeIndex),
                mCursor.getString(dueDateIndex),
                mCursor.getString(classNameIndex));
        AssignmentsContent.ITEM_MAP.put(Integer.toString(item.id), item);



        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String assignmentName = mCursor.getString(assignmentNameIndex);
        int assignmentType = mCursor.getInt(assignmentTypeIndex);
        String assignmentTypeString;
        if (assignmentType == AssignmentItem.QUIZ) {
            assignmentTypeString = " (Quiz)";
        } else if (assignmentType == AssignmentItem.EXAM) {
            assignmentTypeString = " (Exam)";
        } else {
            assignmentTypeString = " (Regular Assignment)";
        }

        //Set values
        holder.itemView.setTag(item);
        holder.assignmentNameView.setText(assignmentName + assignmentTypeString);
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
    class AssignmentsViewHolder extends RecyclerView.ViewHolder{

        // Class variables for the task description and priority TextViews
        TextView assignmentNameView;
        TextView numView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public AssignmentsViewHolder(View itemView) {
            super(itemView);

            assignmentNameView = (TextView) itemView.findViewById(R.id.content);
            numView = (TextView) itemView.findViewById(R.id.id_text);
        }
    }


}
