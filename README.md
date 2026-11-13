# Student Course Management System

**COS 201 — Programming I · Mid-Semester Lab Assessment**
**MIVA Open University · Department of Computer Science**

A console-based Java application for recording the courses a student has
registered, reviewing them, searching by course code, calculating the total
credit load, and saving or loading the course list from disk.

This version keeps the simple menu-driven design of the original project while
adding the missing persistence operations, recursive processing, and safe input
handling.

---

## 1. Introduction

The Student Course Management System is a small Java application that manages a
student's registered courses. Each course has a unique code, a name and a number
of credit units. The program presents a seven-option menu, stores records in an
`ArrayList` while it runs, and persists them to a plain-text file so the data
survives between sessions.

The project demonstrates fundamental Java concepts — classes and objects,
collections, loops and conditionals, methods, recursion, file handling and
exception handling — in a single, complete console program.

---

## 2. Objectives

- Add courses with a code, name and positive credit unit.
- Display every registered course.
- Search for a course by code (case-insensitive).
- Compute the total credit units of all registered courses.
- Save the course list to a file and load it back later.
- Reject invalid input without crashing the program.
- Apply core Java programming concepts to a realistic mini-system.

---

## 3. Features

| # | Feature | Behaviour |
| :-: | :--- | :--- |
| 1 | **Add Course** | Captures code, name and credit unit. Rejects blank fields, duplicate codes and non-positive credit units. |
| 2 | **Display All Courses** | Prints every course with its position and a count of registered courses. |
| 3 | **Search Course** | Finds a course by code using a case-insensitive recursive search. |
| 4 | **Calculate Total Credit Units** | Sums all credit units using recursion. |
| 5 | **Save Courses to File** | Writes the course list to `courses.txt` in pipe-delimited format. |
| 6 | **Load Courses from File** | Reads `courses.txt`, reports and skips malformed lines, and replaces the in-memory list only after a successful read. |
| 7 | **Exit** | Closes the scanner and ends the program cleanly. |

The saved list is also restored automatically at start-up, so the previous
session is available immediately.

---

## 4. Tools and Technologies

- **Language:** Java (JDK 8 or newer)
- **Compiler / runtime:** `javac` and `java`
- **Collections:** `java.util.ArrayList`
- **Input:** `java.util.Scanner`
- **File handling:** `java.nio.file.Files`, `BufferedReader`, `BufferedWriter`
- **Character encoding:** UTF-8
- **Build tools:** none required — plain `javac`/`java` commands

---

## 5. Project Structure

```text
StudentCourseManagmentSystem/
├── README.md
├── .gitignore
└── src/
    └── com/
        └── mycompany/
            └── studentcoursemanagmentsystem/
                ├── Course.java                      # Course model + validation + file format
                └── StudentCourseManagmentSystem.java # Menu, operations and file I/O
```

`courses.txt` is created at runtime and is intentionally ignored by Git.

---

## 6. Build and Run

### Standard build (recommended)

From the repository root:

```bash
javac -d out $(find src -name "*.java")
java -cp out com.mycompany.studentcoursemanagmentsystem.StudentCourseManagmentSystem
```

On Windows (PowerShell), compile the two files explicitly:

```powershell
javac -d out src\com\mycompany\studentcoursemanagmentsystem\Course.java `
              src\com\mycompany\studentcoursemanagmentsystem\StudentCourseManagmentSystem.java
java -cp out com.mycompany.studentcoursemanagmentsystem.StudentCourseManagmentSystem
```

### Single-file launch (JDK 11+)

No separate compile step:

```bash
cd src
java com/mycompany/studentcoursemanagmentsystem/StudentCourseManagmentSystem.java
```

> Run the program from the directory where you want `courses.txt` to be
> created. When using the standard build from the repository root, the file
> appears in the root.

---

## 7. Sample Session

```text
===== STUDENT COURSE MANAGEMENT SYSTEM =====
1. Add Course
2. Display All Courses
3. Search Course
4. Calculate Total Credit Units
5. Save Courses to File
6. Load Courses from File
7. Exit
Enter your choice: 1
Enter course code: COS101
Enter course name: Intro to CS
Enter credit unit: 3
Course added successfully!

Enter your choice: 2

===== COURSES =====
1. COS101 - Intro to CS (3 units)
Total: 1 course(s)

Enter your choice: 3
Enter course code to search: cos101
Course found: COS101 - Intro to CS (3 units)

Enter your choice: 4
Total registered courses: 1
Total Credit Units: 3

Enter your choice: 5
Saved 1 course(s) to courses.txt.

Enter your choice: 7
Exiting program...
```

On the next run the same course is available automatically, or it can be
reloaded explicitly with option **6**.

---

## 8. Data Storage Format

Courses are stored one per line as:

```text
CODE|NAME|CREDIT_UNIT
```

Example:

```text
COS101|Intro to CS|3
COS202|Data Structures|4
```

Rules applied when saving and loading:

- Course codes are trimmed and converted to **uppercase**.
- Names are trimmed; any `|` inside a name is replaced with `/` so the record
  remains loadable.
- Blank lines are ignored.
- Lines with the wrong number of fields, an invalid number, or invalid field
  values are reported and skipped — one bad line does not stop the whole load.
- The current list is replaced only after the file has been read successfully,
  so a failed read leaves existing data intact.

---

## 9. Input Validation and Error Handling

| Situation | Response |
| :--- | :--- |
| Text entered for a menu option or credit unit | `Invalid input. Please enter a whole number.` and the prompt repeats. |
| Empty course code or name | `Input cannot be blank. Try again.` and the prompt repeats. |
| Zero or negative credit unit | `Credit unit must be greater than zero.` and the prompt repeats. |
| Duplicate course code (any case) | The add is refused with a message naming the existing code. |
| `courses.txt` missing during manual load | `No saved file found at courses.txt.` |
| Unreadable file (I/O error) | The error message is printed; in-memory data is unchanged. |
| Malformed line while loading | The line number and reason are printed; the line is skipped. |
| End of piped input | The program prints a message and exits instead of throwing an exception. |

---

## 10. Java Concepts Applied

- **Classes and objects:** `Course` models one record; objects are stored in a
  collection.
- **Encapsulation:** fields are `private final`; access is through getters.
- **Constructor validation:** invalid courses cannot be created.
- **Collections:** `ArrayList<Course>` holds a dynamic list of records.
- **Recursion:** both search (`findCourseRecursive`) and total-credit
  calculation (`calculateTotalCreditsRecursive`) use a base case and a
  recursive step.
- **Loops and conditionals:** the menu loop, retry loops and `switch` statement.
- **Methods:** each menu operation is a separate, focused method.
- **File handling:** UTF-8 `BufferedReader`/`BufferedWriter` with
  try-with-resources.
- **Exception handling:** `NumberFormatException`, `IllegalArgumentException`
  and `IOException` are caught and converted into helpful user messages.
- **String processing:** trimming, case normalization and delimiter sanitizing.

---

## 11. Testing Performed

| Scenario | Expected result | Result |
| :--- | :--- | :--- |
| Add a valid course | Course stored; success message | Pass |
| Add with blank code | Prompt repeats; nothing stored | Pass |
| Add with text as credit unit | Prompt repeats; no crash | Pass |
| Add with `0` or `-5` credit units | Rejected; prompt repeats | Pass |
| Add a duplicate code (`cos101` vs `COS101`) | Rejected with message | Pass |
| Display an empty list | `No courses recorded.` | Pass |
| Display after adding | Course listed with count | Pass |
| Search using a different case | Course found | Pass |
| Search for an unknown code | `Course not found.` | Pass |
| Total credits | Correct recursive sum | Pass |
| Save then reload | Identical course list restored | Pass |
| Load a file containing bad lines | Bad lines reported and skipped | Pass |
| Start with an existing `courses.txt` | Data restored automatically | Pass |
| Invalid menu option | Message shown; menu repeats | Pass |

---

## 12. Limitations and Future Improvements

- Courses cannot be edited or deleted yet; only add, view and search are
  supported.
- The store is a single shared text file intended for one user at a time.
- There is no sorting or duplicate-name detection (codes are the unique key).
- The package name keeps the original spelling `managment` for compatibility
  with the upstream repository.
- Possible extensions: edit/delete operations, sorting by code or credit unit,
  a graphical interface, and per-student data files.

---

## 13. Conclusion

The Student Course Management System meets the seven operations required by the
lab assessment: adding, viewing, searching, calculating credit units, saving,
loading and exiting. It also demonstrates defensive programming — invalid
input is rejected rather than crashing the program, persistence is robust
against malformed records, and both search and summation use recursion. The
project provides a solid foundation for a larger course-management application.

---

## 14. Attribution

- Original project: [umahirebecca-tech/StudentCourseManagmentSystem](https://github.com/umahirebecca-tech/StudentCourseManagmentSystem)
- Improvements, validation, persistence and documentation: this fork.

Submitted as part of the COS 201 Mid-Semester Lab Assessment at **MIVA Open
University**.
