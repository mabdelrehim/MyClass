package com.example.android.myclass.data;

public class StudentItem {

    public int id;
    public String studentName;
    public String studentClass;
    public String email;
    public String parentEmail;
    public int daysAbsent;
    public String details;


    public StudentItem(int id, String studentName, String studentClass,
                       String email, String parentEmail, int daysAbsent) {
        this.id = id;
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.email = email;
        this.parentEmail = parentEmail;
        this.daysAbsent = daysAbsent;
        this.details = "Class: " + this.studentClass + "\n\n" +
                "Email: " + this.email + "\n\n" +
                "Parent's Email: " + this.parentEmail + "\n\n" +
                "Days Absent: " + Integer.toString(this.daysAbsent) + "\n\n";
    }

    @Override
    public String toString() {
        return studentName;
    }
}
