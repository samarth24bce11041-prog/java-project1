package edu.ccrm.io;
import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import edu.ccrm.util.CsvParserUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
public class DataPersistenceServiceImpl implements DataPersistenceService {
    private final DataStore dataStore = DataStore.getInstance();
    private static final String STUDENTS_FILE = "students.csv";
    private static final String COURSES_FILE = "courses.csv";
    @Override
    public void exportData(String directoryPath) throws IOException {
        Path dir = Paths.get(directoryPath);
        Files.createDirectories(dir);
        Path studentsPath = dir.resolve(STUDENTS_FILE);
        List<String> studentLines = dataStore.getStudents().stream()
                .map(CsvParserUtil::studentToCsv)
                .collect(Collectors.toList());
        Files.write(studentsPath, studentLines);
        Path coursesPath = dir.resolve(COURSES_FILE);
        List<String> courseLines = dataStore.getCourses().stream()
                .map(CsvParserUtil::courseToCsv)
                .collect(Collectors.toList());
        Files.write(coursesPath, courseLines);
        System.out.println("Data exported successfully to " + directoryPath);
    }
    @Override
    public void importData(String directoryPath) throws IOException {
        Path dir = Paths.get(directoryPath);
        Path studentsPath = dir.resolve(STUDENTS_FILE);
        if (Files.exists(studentsPath)) {
            try (var lines = Files.lines(studentsPath)) {
                lines.map(CsvParserUtil::csvToStudent).forEach(dataStore::addStudent);
            }
        }
        Path coursesPath = dir.resolve(COURSES_FILE);
        if (Files.exists(coursesPath)) {
            try(var lines = Files.lines(coursesPath)) {
                lines.forEach(line -> {
                    String[] fields = CsvParserUtil.csvToCourseFields(line);
                    Course.Builder builder = new Course.Builder(fields[0], fields[1], Integer.parseInt(fields[2]), Semester.valueOf(fields[3]));
                    builder.department(fields[4]);
                    dataStore.addCourse(builder.build());
                });
            }
        }
        System.out.println("Data imported successfully from " + directoryPath);
    }
}