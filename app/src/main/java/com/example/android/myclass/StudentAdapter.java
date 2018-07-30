package com.example.android.myclass;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.android.myclass.data.MyDBHelper;
import com.example.android.myclass.data.StudentItem;

import java.util.ArrayList;

/**
 * Created by lenovo on 7/24/2018.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentsAttendanceHolder> {

    ArrayList<StudentItem> studentList;
    Context context;
    MyDBHelper mDatabaseHelper;

    public StudentAdapter(Activity context, ArrayList<StudentItem> list) {
        this.context = context;
        studentList = new ArrayList<>();
        studentList = list;
        mDatabaseHelper = new MyDBHelper(context);
    }


    @Override
    public StudentsAttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.attendance_list_item, parent, false);

        return new StudentsAttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentsAttendanceHolder holder,final int position) {
        holder.stdName.setText(studentList.get(position).getName());
        final StudentItem currentStudent = studentList.get(position);

        if (currentStudent.getmIsChecked())
            holder.chbox.setChecked(true);
        else
            currentStudent.setmIsChecked(false);
        holder.setICheckChangeListener(new ICheckChangeListener() {
            @Override
            public void onItemChecked(int position, boolean value) {
                studentList.get(position).setmIsChecked(value);
                if(value){
                    int x = mDatabaseHelper.getAbsence(studentList.get(position).id);
                    mDatabaseHelper.updateAbsence(studentList.get(position).id, x);
                }
                else if(!value){
                    int x = mDatabaseHelper.getAbsence(studentList.get(position).id);
                    mDatabaseHelper.updateAbsence2(studentList.get(position).id, x);
                }}
        });


//        holder.chbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.chbox.isChecked()) {
//                    studentList.get(position).setmIsChecked(true);
////                    int count = mDatabaseHelper.getAbsence(studentList.get(position).id);
////                    mDatabaseHelper.updateAbsence(studentList.get(position).id,count );
//                } else
//                    studentList.get(position).setmIsChecked(false);
//                    int x = mDatabaseHelper.getAbsence(studentList.get(position).id);
//                    mDatabaseHelper.updateAbsence(studentList.get(position).id,x );


//        });
//                if(studentList.get(position).getmIsChecked()== false) {
//                    int x = mDatabaseHelper.getAbsence(studentList.get(position).id);
//                    mDatabaseHelper.updateAbsence(studentList.get(position).id, x);
//                }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void addData(ArrayList<StudentItem> list) {
        studentList.clear();
        studentList.addAll(list);
        notifyDataSetChanged();
    }


    class StudentsAttendanceHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView stdName;
        CheckBox chbox;
        private ICheckChangeListener iCheckChangeListener;
        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public StudentsAttendanceHolder(View itemView) {
            super(itemView);
            stdName = (TextView) itemView.findViewById(R.id.StudentName_view);
            chbox = (CheckBox) itemView.findViewById(R.id.checkBox_view);

            chbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    iCheckChangeListener.onItemChecked(getAdapterPosition(), b);
                }
            });
        }
        void setICheckChangeListener(ICheckChangeListener iCheckChangeListener) {
            this.iCheckChangeListener = iCheckChangeListener;
        }
    }




}
