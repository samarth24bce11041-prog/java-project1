package edu.ccrm.service.impl;
import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.CourseService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
public class CourseServiceImpl implements CourseService {
    private final DataStore dataStore = DataStore.getInstance();
    @Override
    public void addCourse(Course course) {
        dataStore.addCourse(course);
    }
    @Override
    public Optional<Course> findCourseByCode(String courseCode) {
        return dataStore.getCourses().stream()
                .filter(course -> course.getCode().equalsIgnoreCase(courseCode))
                .findFirst();
    }
    @Override
    public List<Course> searchCoursesByInstructor(String instructorName) {
        return dataStore.getCourses().stream()
                .filter(course -> course.getInstructor() != null &&
                                  course.getInstructor().getFullName().toLowerCase().contains(instructorName.toLowerCase()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Course> searchCoursesByDepartment(String department) {
        return dataStore.getCourses().stream()
                .filter(course -> course.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
    @Override
    public List<Course> filterCoursesBySemester(Semester semester) {
        return dataStore.getCourses().stream()
                .filter(course -> course.getSemester() == semester)
                .collect(Collectors.toList());
    }
    @Override
    public List<Course> findAllCourses() {
        return dataStore.getCourses();
    }
}