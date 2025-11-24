package edu.ccrm.service;
import edu.ccrm.domain.Instructor;
import java.util.List;
import java.util.Optional;
public interface InstructorService {
    List<Instructor> findAllInstructors();
    Optional<Instructor> findInstructorByStaffId(String staffId);
}