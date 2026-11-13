package com.mycompany.studentcoursemanagmentsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console front-end for the Student Course Management System.
 *
 * <p>The application keeps courses in memory while it runs and can save them
 * to, or load them from, a plain-text file called {@code courses.txt}.</p>
 */
public class StudentCourseManagmentSystem {

    private static final String DATA_FILE = "courses.txt";
    private static final ArrayList<Course> courses = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Restore any previously saved courses without cluttering the screen.
        loadCourses(false);

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

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
                    saveCourses();
                    break;
                case 6:
                    loadCourses(true);
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-7.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== STUDENT COURSE MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Course");
        System.out.println("2. Display All Courses");
        System.out.println("3. Search Course");
        System.out.println("4. Calculate Total Credit Units");
        System.out.println("5. Save Courses to File");
        System.out.println("6. Load Courses from File");
        System.out.println("7. Exit");
    }

    private static void addCourse() {
        String code = readNonBlank("Enter course code: ");

        if (findCourseRecursive(code, 0) != null) {
            System.out.println("A course with code " + code.toUpperCase() + " already exists.");
            return;
        }

        String name = readNonBlank("Enter course name: ");
        int credit = readPositiveInt("Enter credit unit: ");

        courses.add(new Course(code, name, credit));
        System.out.println("Course added successfully!");
    }

    private static void displayCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses recorded.");
            return;
        }

        System.out.println("\n===== COURSES =====");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }
        System.out.println("Total: " + courses.size() + " course(s)");
    }

    private static void searchCourse() {
        String code = readNonBlank("Enter course code to search: ");
        Course course = findCourseRecursive(code, 0);

        if (course == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("Course found: " + course);
        }
    }

    /**
     * Case-insensitive recursive search. The base case is the end of the list.
     */
    private static Course findCourseRecursive(String code, int index) {
        if (index >= courses.size()) {
            return null;
        }

        Course course = courses.get(index);
        if (course.getCourseCode().equalsIgnoreCase(code.trim())) {
            return course;
        }

        return findCourseRecursive(code, index + 1);
    }

    private static void calculateTotalCredits() {
        if (courses.isEmpty()) {
            System.out.println("No courses available to calculate.");
            return;
        }

        int total = calculateTotalCreditsRecursive(0);
        System.out.println("Total registered courses: " + courses.size());
        System.out.println("Total Credit Units: " + total);
    }

    /**
     * Recursively sums the credit units of every course in the list.
     */
    private static int calculateTotalCreditsRecursive(int index) {
        if (index >= courses.size()) {
            return 0;
        }

        return courses.get(index).getCreditUnit() + calculateTotalCreditsRecursive(index + 1);
    }

    private static void saveCourses() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(DATA_FILE), StandardCharsets.UTF_8)) {
            for (Course course : courses) {
                writer.write(course.toFileFormat());
                writer.newLine();
            }
            System.out.println("Saved " + courses.size() + " course(s) to " + DATA_FILE + ".");
        } catch (IOException e) {
            System.out.println("Could not save courses: " + e.getMessage());
        }
    }

    /**
     * Reads courses from disk. Invalid lines are reported and skipped, and the
     * in-memory list is only replaced when the file was read successfully.
     *
     * @param announce {@code true} when the user explicitly chose the load
     *                 option; {@code false} for the silent start-up load
     */
    private static void loadCourses(boolean announce) {
        Path path = Paths.get(DATA_FILE);

        if (!Files.exists(path)) {
            if (announce) {
                System.out.println("No saved file found at " + DATA_FILE + ".");
            }
            return;
        }

        List<Course> loaded = new ArrayList<>();
        int skipped = 0;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    loaded.add(Course.fromFileFormat(line));
                } catch (IllegalArgumentException e) {
                    skipped++;
                    if (announce) {
                        System.out.println("Skipped line " + lineNumber + ": " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load courses: " + e.getMessage());
            return;
        }

        courses.clear();
        courses.addAll(loaded);

        if (announce) {
            System.out.println("Loaded " + loaded.size() + " course(s) from " + DATA_FILE + ".");
            if (skipped > 0) {
                System.out.println("Skipped " + skipped + " invalid line(s).");
            }
        }
    }

    /**
     * Reads one line of input, exiting cleanly if the input stream ends
     * (for example when the program is piped from a file).
     */
    private static String readLineOrExit(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println("\nNo more input. Exiting program...");
            scanner.close();
            System.exit(0);
        }
        return scanner.nextLine();
    }

    private static String readNonBlank(String prompt) {
        while (true) {
            String value = readLineOrExit(prompt).trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be blank. Try again.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            String value = readLineOrExit(prompt).trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Credit unit must be greater than zero.");
        }
    }
}
