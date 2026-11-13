package com.mycompany.studentcoursemanagmentsystem;

/**
 * Represents a single course in the Student Course Management System.
 *
 * <p>A course stores its code, name and credit unit. The constructor validates
 * every field so that an invalid course object can never be created.</p>
 */
public class Course {

    private static final String FILE_SEPARATOR = "|";

    private final String courseCode;
    private final String courseName;
    private final int creditUnit;

    public Course(String courseCode, String courseName, int creditUnit) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be blank.");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be blank.");
        }
        if (creditUnit <= 0) {
            throw new IllegalArgumentException("Credit unit must be greater than zero.");
        }

        this.courseCode = courseCode.trim().toUpperCase();
        // "|" is the file separator, so replace it to keep records loadable.
        this.courseName = courseName.trim().replace(FILE_SEPARATOR, "/");
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

    /**
     * Converts this course into one line of the data file:
     * {@code CODE|NAME|CREDIT_UNIT}.
     */
    public String toFileFormat() {
        return courseCode + FILE_SEPARATOR + courseName + FILE_SEPARATOR + creditUnit;
    }

    /**
     * Rebuilds a course from a line produced by {@link #toFileFormat()}.
     *
     * @throws IllegalArgumentException if the line is blank, does not have
     *                                  exactly three fields, or contains an
     *                                  invalid credit unit
     */
    public static Course fromFileFormat(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty record.");
        }

        String[] parts = line.split("\\|", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Expected 3 fields but found " + parts.length + ".");
        }

        try {
            return new Course(parts[0], parts[1], Integer.parseInt(parts[2].trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Credit unit is not a valid number: " + parts[2].trim());
        }
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + creditUnit + " units)";
    }
}
