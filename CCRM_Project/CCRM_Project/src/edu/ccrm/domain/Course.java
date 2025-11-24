package edu.ccrm.domain;
public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private Instructor instructor;
    private final Semester semester;
    private final String department;
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.semester = builder.semester;
        this.department = builder.department;
        this.instructor = builder.instructor;
    }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public Instructor getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
    @Override
    public String toString() {
        String instructorName = (instructor != null) ? instructor.getFullName() : "Not Assigned";
        return String.format("Course[Code=%s, Title='%s', Credits=%d, Instructor=%s]",
                code, title, credits, instructorName);
    }
    public static class Builder {
        private final String code;
        private final String title;
        private final int credits;
        private final Semester semester;
        private String department = "General";
        private Instructor instructor = null;
        public Builder(String code, String title, int credits, Semester semester) {
            this.code = code;
            this.title = title;
            this.credits = credits;
            this.semester = semester;
        }
        public Builder department(String department) {
            this.department = department;
            return this;
        }
        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }
        public Course build() {
            return new Course(this);
        }
    }
}