package edu.ccrm.service.impl;
import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Instructor;
import edu.ccrm.service.InstructorService;
import java.util.List;
import java.util.Optional;
public class InstructorServiceImpl implements InstructorService {
    private final DataStore dataStore = DataStore.getInstance();
    @Override
    public List<Instructor> findAllInstructors() {
        return dataStore.getInstructors();
    }
    @Override
    public Optional<Instructor> findInstructorByStaffId(String staffId) {
        return dataStore.getInstructors().stream()
                .filter(inst -> inst.getStaffId().equalsIgnoreCase(staffId))
                .findFirst();
    }
}