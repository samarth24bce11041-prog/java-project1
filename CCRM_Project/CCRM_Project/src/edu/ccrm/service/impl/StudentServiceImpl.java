package edu.ccrm.service.impl;
import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Student;
import edu.ccrm.service.StudentService;
import java.util.List;
import java.util.Optional;
public class StudentServiceImpl implements StudentService {
    private final DataStore dataStore = DataStore.getInstance();
    @Override
    public void addStudent(Student student) {
        dataStore.addStudent(student);
    }
    @Override
    public Optional<Student> findStudentByRegNo(String regNo) {
        return dataStore.getStudents().stream()
                .filter(student -> student.getRegNo().equalsIgnoreCase(regNo))
                .findFirst();
    }
    @Override
    public List<Student> findAllStudents() {
        return dataStore.getStudents();
    }
}