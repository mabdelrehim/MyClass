package com.example.android.myclass.data;

public class AssignmentStudentItem {

    public int id;
    public String studentName;
    public int studentId;
    public String className;
    public int assignmentId;
    public int studentGrade;
    public String comments;

    public AssignmentStudentItem (int id, String studentName, int studentId, String className,
                                  int assignmentId, int studentGrade, String comments) {
        this.id = id;
        this.studentName = studentName;
        this.studentId = studentId;
        this.className = className;
        this.assignmentId = assignmentId;
        this.studentGrade = studentGrade;
        this.comments = comments;
    }

}
