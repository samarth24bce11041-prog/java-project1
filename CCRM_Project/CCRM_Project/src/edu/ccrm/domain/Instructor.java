package edu.ccrm.domain;
import java.time.LocalDate;
public class Instructor extends Person {
    private String staffId;
    private String specialization;
    public Instructor(int id, String fullName, String email, LocalDate dateOfBirth, String staffId) {
        super(id, fullName, email, dateOfBirth);
        this.staffId = staffId;
    }
    public String getStaffId() { return staffId; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    @Override
    public String getProfileSummary() {
        return String.format("Instructor | Name: %s, Staff ID: %s, Email: %s",
                super.fullName, this.staffId, super.email);
    }
    @Override
    public String toString() {
        return getProfileSummary();
    }
}