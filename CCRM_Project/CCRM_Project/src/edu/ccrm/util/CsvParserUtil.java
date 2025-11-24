package edu.ccrm.util;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import java.time.LocalDate;
public class CsvParserUtil {
    private static final String DELIMITER = ",";
    public static String studentToCsv(Student s) {
        return String.join(DELIMITER,
                String.valueOf(s.getId()),
                s.getFullName(),
                s.getEmail(),
                s.getDateOfBirth().toString(),
                s.getRegNo());
    }
    public static Student csvToStudent(String csvLine) {
        String[] fields = csvLine.split(DELIMITER);
        return new Student(
                Integer.parseInt(fields[0]),
                fields[1],
                fields[2],
                LocalDate.parse(fields[3]),
                fields[4]);
    }
    public static String courseToCsv(Course c) {
        String instructorId = (c.getInstructor() != null) ? String.valueOf(c.getInstructor().getId()) : "null";
        return String.join(DELIMITER,
                c.getCode(),
                c.getTitle(),
                String.valueOf(c.getCredits()),
                c.getSemester().name(),
                c.getDepartment(),
                instructorId);
    }
    public static String[] csvToCourseFields(String csvLine) {
        return csvLine.split(DELIMITER);
    }
}