
package com.mycompany.studentcoursemanagmentsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentCourseManagmentSystem {

    static ArrayList<Course> courses = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== STUDENT COURSE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Course");
            System.out.println("2. Display All Courses");
            System.out.println("3. Search Course");
            System.out.println("4. Calculate Total Credit Units");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    displayCourses();
                    break;
                case 3:
                    searchCourse();
                    break;
                case 4:
                    calculateTotalCredits();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);
    }

    public static void addCourse() {

        System.out.print("Enter course code: ");
        String code = scanner.nextLine();

        System.out.print("Enter course name: ");
        String name = scanner.nextLine();

        System.out.print("Enter credit unit: ");
        int credit = scanner.nextInt();
        scanner.nextLine();

        Course course = new Course(code, name, credit);
        courses.add(course);

        System.out.println("Course added successfully!");
    }

    public static void displayCourses() {

        if (courses.isEmpty()) {
            System.out.println("No courses recorded.");
        } else {
            System.out.println("\n===== COURSES =====");

            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }

    public static void searchCourse() {

        System.out.print("Enter course code to search: ");
        String code = scanner.nextLine();

        boolean found = false;

        for (Course course : courses) {

            if (course.getCourseCode().equalsIgnoreCase(code)) {
                System.out.println("Course found: " + course);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Course not found.");
        }
    }

    public static void calculateTotalCredits() {

        int total = 0;

        for (Course course : courses) {
            total += course.getCreditUnit();
        }

        System.out.println("Total Credit Units: " + total);
    }
}