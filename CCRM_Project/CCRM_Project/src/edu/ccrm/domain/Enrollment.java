package edu.ccrm.domain;
import java.time.LocalDateTime;
public class Enrollment {
    private Student student;
    private Course course;
    private Grade grade;
    private final LocalDateTime enrollmentDate;
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
        this.grade = Grade.PENDING;
    }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Grade getGrade() { return grade; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    @Override
    public String toString() {
        return String.format("Enrollment[Course=%s, Grade=%s]",
                course.getCode(), grade.toString());
    }
}