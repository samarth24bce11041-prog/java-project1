package edu.ccrm.cli;
import edu.ccrm.domain.*;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.BackupServiceImpl;
import edu.ccrm.io.DataPersistenceService;
import edu.ccrm.io.DataPersistenceServiceImpl;
import edu.ccrm.service.*;
import edu.ccrm.service.impl.*;
import edu.ccrm.util.MaxCreditLimitExceededException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
public class CCRMApplication {
    private final CourseService courseService = new CourseServiceImpl();
    private final StudentService studentService = new StudentServiceImpl();
    private final InstructorService instructorService = new InstructorServiceImpl();
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private final DataPersistenceService persistenceService = new DataPersistenceServiceImpl();
    private final BackupService backupService = new BackupServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    private static final String DATA_DIRECTORY = "data";
    private static final String BACKUP_DIRECTORY = "backups";
    public static void main(String[] args) {
        CCRMApplication app = new CCRMApplication();
        app.run();
    }
    public void run() {
        boolean running = true;
        do {
            displayMainMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> manageCourses();
                    case 2 -> manageStudents();
                    case 3 -> manageEnrollmentsAndGrades();
                    case 4 -> handleDataExportImport();
                    case 5 -> handleBackup();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        } while (running);
        System.out.println("Thank you for using CCRM. Goodbye!");
        scanner.close();
    }
    private void displayMainMenu() {
        System.out.println("\n===== Campus Course & Records Manager =====");
        System.out.println("1. Manage Courses");
        System.out.println("2. Manage Students");
        System.out.println("3. Manage Enrollments & Grades");
        System.out.println("4. Import/Export Data");
        System.out.println("5. Backup Utilities");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    private void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. List All Courses");
        System.out.println("3. Search Courses by Instructor");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            addNewCourse();
        } else if (choice == 2) {
            System.out.println("\nListing all courses...");
            List<Course> courses = courseService.findAllCourses();
            courses.forEach(System.out::println);
        } else if (choice == 3) {
            System.out.print("Enter instructor name to search: ");
            String name = scanner.nextLine();
            List<Course> foundCourses = courseService.searchCoursesByInstructor(name);
            if (foundCourses.isEmpty()) {
                System.out.println("No courses found for an instructor with that name.");
            } else {
                System.out.println("Found courses:");
                foundCourses.forEach(course -> System.out.println(course));
            }
        }
    }
    private void addNewCourse() {
        try {
            System.out.println("\n--- Add New Course ---");
            System.out.print("Enter Course Code (e.g., CS101): ");
            String code = scanner.nextLine();
            System.out.print("Enter Course Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Credits (e.g., 4): ");
            int credits = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Semester (SPRING, SUMMER, or FALL): ");
            Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter Department: ");
            String department = scanner.nextLine();
            Course.Builder courseBuilder = new Course.Builder(code, title, credits, semester)
                                             .department(department);
            System.out.print("\nAssign an instructor now? (yes/no): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                System.out.println("Available Instructors:");
                List<Instructor> instructors = instructorService.findAllInstructors();
                if (instructors.isEmpty()) {
                    System.out.println("No instructors available to assign.");
                } else {
                    instructors.forEach(System.out::println);
                    System.out.print("\nEnter the Staff ID of the instructor to assign: ");
                    String staffId = scanner.nextLine();
                    Optional<Instructor> instructorOpt = instructorService.findInstructorByStaffId(staffId);
                    if (instructorOpt.isPresent()) {
                        courseBuilder.instructor(instructorOpt.get());
                        System.out.println("Instructor assigned successfully.");
                    } else {
                        System.out.println("Instructor with that Staff ID not found. Course will be created without an instructor.");
                    }
                }
            }
            Course newCourse = courseBuilder.build();
            courseService.addCourse(newCourse);
            System.out.println("\n✅ Success: Course '" + newCourse.getTitle() + "' added.");
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: Invalid semester. Please use SPRING, SUMMER, or FALL.");
        } catch (Exception e) {
            System.err.println("❌ Error: Invalid input. Please try again.");
            scanner.nextLine();
        }
    }
    private void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View Student Transcript");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            addNewStudent();
        } else if (choice == 2) {
            printStudentTranscript();
        }
    }
    private void addNewStudent() {
        try {
            System.out.println("\n--- Add New Student ---");
            System.out.print("Enter Student ID (e.g., 4): ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String fullName = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Registration Number (e.g., S004): ");
            String regNo = scanner.nextLine();
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine());
            Student newStudent = new Student(id, fullName, email, dob, regNo);
            studentService.addStudent(newStudent);
            System.out.println("\n✅ Success: Student '" + newStudent.getFullName() + "' added.");
        } catch (DateTimeParseException e) {
            System.err.println("❌ Error: Invalid date format. Please use YYYY-MM-DD.");
        } catch (Exception e) {
            System.err.println("❌ Error: Invalid input. Please try again.");
            scanner.nextLine();
        }
    }
    private void manageEnrollmentsAndGrades() {
        System.out.println("\n--- Enrollment & Grading ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Record Grade for Student");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            enrollStudent();
        } else if (choice == 2) {
            recordGrade();
        }
    }
    private void enrollStudent() {
        System.out.print("Enter Student Registration No (e.g., S001): ");
        String regNo = scanner.nextLine();
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        if (studentOpt.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter Course Code to enroll in (e.g., PHY201): ");
        String courseCode = scanner.nextLine();
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        if (courseOpt.isEmpty()) {
            System.out.println("Course not found.");
            return;
        }
        try {
            enrollmentService.enrollStudent(studentOpt.get(), courseOpt.get());
        } catch (MaxCreditLimitExceededException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void recordGrade() {
        try {
            System.out.print("Enter Student Registration No (e.g., S001): ");
            String regNo = scanner.nextLine();
            Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
            if (studentOpt.isEmpty()) {
                System.out.println("Student not found.");
                return;
            }
            System.out.print("Enter Course Code (e.g., PHY201): ");
            String courseCode = scanner.nextLine();
            Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
            if (courseOpt.isEmpty()) {
                System.out.println("Course not found.");
                return;
            }
            System.out.print("Enter Grade (S, A, B, C, D, E, or F): ");
            Grade grade = Grade.valueOf(scanner.nextLine().toUpperCase());
            enrollmentService.recordGrade(studentOpt.get(), courseOpt.get(), grade);
            System.out.println("\n✅ Success: Grade recorded for student " + regNo + " in course " + courseCode);
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: Invalid grade entered. Please use S, A, B, etc.");
        } catch (Exception e) {
            System.err.println("❌ An unexpected error occurred. Please try again.");
            scanner.nextLine();
        }
    }
    private void printStudentTranscript() {
        System.out.print("Enter Student Registration No to view transcript (e.g., S001): ");
        String regNo = scanner.nextLine();
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            System.out.println("\n--- Transcript for " + student.getFullName() + " ---");
            System.out.println("Registration No: " + student.getRegNo());
            if (student.getEnrolledCourses().isEmpty()) {
                System.out.println("No courses enrolled yet.");
            } else {
                for (Enrollment enrollment : student.getEnrolledCourses()) {
                    System.out.printf("  - Course: %s (%s), Credits: %d, Grade: %s\n",
                        enrollment.getCourse().getTitle(),
                        enrollment.getCourse().getCode(),
                        enrollment.getCourse().getCredits(),
                        enrollment.getGrade());
                }
            }
        } else {
            System.out.println("Student not found.");
        }
    }
    private void handleDataExportImport() {
        System.out.println("\n--- Data Utilities ---");
        System.out.println("1. Export data to '" + DATA_DIRECTORY + "'");
        System.out.println("2. Import data from '" + DATA_DIRECTORY + "'");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        try {
            if (choice == 1) {
                persistenceService.exportData(DATA_DIRECTORY);
            } else if (choice == 2) {
                persistenceService.importData(DATA_DIRECTORY);
            }
        } catch (IOException e) {
            System.err.println("File operation failed: " + e.getMessage());
        }
    }
    private void handleBackup() {
        System.out.println("\n--- Backup Utilities ---");
        System.out.println("1. Create new backup of '" + DATA_DIRECTORY + "'");
        System.out.println("2. Get total size of '" + BACKUP_DIRECTORY + "' folder");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        try {
            if (choice == 1) {
                backupService.performBackup(DATA_DIRECTORY, BACKUP_DIRECTORY);
            } else if (choice == 2) {
                long size = backupService.getDirectorySize(BACKUP_DIRECTORY);
                System.out.printf("Total size of backup directory is: %.2f KB\n", size / 1024.0);
            }
        } catch (IOException e) {
            System.err.println("Backup operation failed: " + e.getMessage());
        }
    }
}