package edu.ccrm.domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Student extends Person {
    private String regNo;
    private boolean isActive;
    private List<Enrollment> enrolledCourses;
    public Student(int id, String fullName, String email, LocalDate dateOfBirth, String regNo) {
        super(id, fullName, email, dateOfBirth);
        this.regNo = regNo;
        this.isActive = true;
        this.enrolledCourses = new ArrayList<>();
    }
    public String getRegNo() { return regNo; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public List<Enrollment> getEnrolledCourses() { return enrolledCourses; }
    @Override
    public String getProfileSummary() {
        return String.format("Student | Name: %s, RegNo: %s, Email: %s",
                super.fullName, this.regNo, super.email);
    }
    @Override
    public String toString() {
        return getProfileSummary();
    }
}