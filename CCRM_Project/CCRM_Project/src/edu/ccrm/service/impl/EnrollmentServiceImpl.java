package edu.ccrm.service.impl;
import edu.ccrm.domain.*;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.util.DuplicateEnrollmentException;
import edu.ccrm.util.MaxCreditLimitExceededException;
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final int MAX_CREDITS_PER_SEMESTER = 20;
    @Override
    public void enrollStudent(Student student, Course course) throws MaxCreditLimitExceededException {
        boolean alreadyEnrolled = student.getEnrolledCourses().stream()
                .anyMatch(enrollment -> enrollment.getCourse().equals(course));
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException("Student " + student.getRegNo() +
                    " is already enrolled in course " + course.getCode());
        }
        int currentCredits = student.getEnrolledCourses().stream()
                .filter(enrollment -> enrollment.getCourse().getSemester() == course.getSemester())
                .mapToInt(enrollment -> enrollment.getCourse().getCredits())
                .sum();
        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException("Cannot enroll. Exceeds max credit limit of "
                    + MAX_CREDITS_PER_SEMESTER + " for the semester.");
        }
        Enrollment newEnrollment = new Enrollment(student, course);
        student.getEnrolledCourses().add(newEnrollment);
        System.out.printf("Success: Student %s enrolled in %s.\n", student.getFullName(), course.getTitle());
    }
    @Override
    public void recordGrade(Student student, Course course, Grade grade) {
        student.getEnrolledCourses().stream()
                .filter(enrollment -> enrollment.getCourse().equals(course))
                .findFirst()
                .ifPresent(enrollment -> enrollment.setGrade(grade));
    }
}