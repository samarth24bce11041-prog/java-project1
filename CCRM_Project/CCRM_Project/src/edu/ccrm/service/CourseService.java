package edu.ccrm.service;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import java.util.List;
import java.util.Optional;
public interface CourseService {
    void addCourse(Course course);
    Optional<Course> findCourseByCode(String courseCode);
    List<Course> searchCoursesByInstructor(String instructorName);
    List<Course> searchCoursesByDepartment(String department);
    List<Course> filterCoursesBySemester(Semester semester);
    List<Course> findAllCourses();
}