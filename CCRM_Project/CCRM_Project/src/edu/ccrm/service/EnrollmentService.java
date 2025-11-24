package edu.ccrm.service;
import edu.ccrm.domain.*;
import edu.ccrm.util.MaxCreditLimitExceededException;
public interface EnrollmentService {
    void enrollStudent(Student student, Course course) throws MaxCreditLimitExceededException;
    void recordGrade(Student student, Course course, Grade grade);
}