package edu.ccrm.config;
import edu.ccrm.domain.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class DataStore {
    private static final DataStore instance = new DataStore();
    private final List<Student> students;
    private final List<Course> courses;
    private final List<Instructor> instructors;
    private DataStore() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        instructors = new ArrayList<>();
        initializeDummyData();
    }
    public static DataStore getInstance() {
        return instance;
    }
    public List<Student> getStudents() { return students; }
    public List<Course> getCourses() { return courses; }
    public List<Instructor> getInstructors() { return instructors; }
    public void addStudent(Student student) { this.students.add(student); }
    public void addCourse(Course course) { this.courses.add(course); }
    public void addInstructor(Instructor instructor) { this.instructors.add(instructor); }
    private void initializeDummyData() {
        Instructor profXavier = new Instructor(101, "Charles Xavier", "prof.x@ccrm.edu", LocalDate.of(1975, 8, 15), "INST01");
        Instructor profLogan = new Instructor(102, "James 'Logan' Howlett", "logan@ccrm.edu", LocalDate.of(1980, 5, 10), "INST02");
        addInstructor(profXavier);
        addInstructor(profLogan);
        Course cs101 = new Course.Builder("CS101", "Intro to Programming", 4, Semester.FALL)
                .department("Computer Science")
                .instructor(profXavier)
                .build();
        Course phys201 = new Course.Builder("PHY201", "Quantum Mechanics", 5, Semester.FALL)
                .department("Physics")
                .instructor(profLogan)
                .build();
        Course math101 = new Course.Builder("MATH101", "Calculus I", 4, Semester.SPRING)
                .department("Mathematics")
                .build();
        addCourse(cs101);
        addCourse(phys201);
        addCourse(math101);
        Student jeanGrey = new Student(1, "Jean Grey", "jean.g@ccrm.edu", LocalDate.of(2002, 3, 22), "S001");
        Student scottSummers = new Student(2, "Scott Summers", "scott.s@ccrm.edu", LocalDate.of(2001, 11, 1), "S002");
        addStudent(jeanGrey);
        addStudent(scottSummers);
    }
}