
package com.mycompany.studentcoursemanagmentsystem;

public class Course {

    private String courseCode;
    private String courseName;
    private int creditUnit;

    public Course(String courseCode, String courseName, int creditUnit) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditUnit = creditUnit;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCreditUnit() {
        return creditUnit;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + creditUnit + " units)";
    }
}