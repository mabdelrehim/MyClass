package com.example.android.myclass.data;

public class AssignmentItem {

    // using integer codes to differentiate assignment types
    public static int REGULAR_ASSIGNMENT = 1;
    public static int QUIZ = 2;
    public static int EXAM = 3;

    public int id;
    public String assignmentName;
    public int assignmentType;
    public int totalGrade;
    public String details;
    public String dateAssigned;
    public String dueDate;
    public String className;

    public AssignmentItem(int id, String assignmentName, int assignmentType, int totalGrade,
                          String details, String dateAssigned, String dueDate, String className) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.assignmentType = assignmentType;
        this.totalGrade = totalGrade;
        this.details = details;
        this.dateAssigned = dateAssigned;
        this.dueDate = dueDate;
        this.className = className;
    }

    @Override
    public String toString() {
        return assignmentName + " (" + assignmentType + ")";
    }

}
