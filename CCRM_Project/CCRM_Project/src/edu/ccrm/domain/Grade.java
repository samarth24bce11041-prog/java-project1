package edu.ccrm.domain;
public enum Grade {
    S("S", 10.0),
    A("A", 9.0),
    B("B", 8.0),
    C("C", 7.0),
    D("D", 6.0),
    E("E", 5.0),
    F("F", 0.0),
    PENDING("Pending", 0.0);
    private final String displayName;
    private final double gradePoint;
    Grade(String displayName, double gradePoint) {
        this.displayName = displayName;
        this.gradePoint = gradePoint;
    }
    public double getGradePoint() {
        return gradePoint;
    }
    @Override
    public String toString() {
        return displayName;
    }
}