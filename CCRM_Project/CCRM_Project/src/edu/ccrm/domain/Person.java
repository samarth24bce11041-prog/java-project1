package edu.ccrm.domain;
import java.time.LocalDate;
public abstract class Person {
    protected int id;
    protected String fullName;
    protected String email;
    protected LocalDate dateOfBirth;
    public Person(int id, String fullName, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public abstract String getProfileSummary();
}