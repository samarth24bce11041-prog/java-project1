package edu.ccrm.io;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
public class BackupServiceImpl implements BackupService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    @Override
    public void performBackup(String sourceDirectoryPath, String backupRootDirectoryPath) throws IOException {
        Path sourceDir = Paths.get(sourceDirectoryPath);
        Path backupRootDir = Paths.get(backupRootDirectoryPath);
        String timestamp = LocalDateTime.now().format(formatter);
        Path backupDestDir = backupRootDir.resolve("backup_" + timestamp);
        Files.createDirectories(backupDestDir);
        try (Stream<Path> stream = Files.walk(sourceDir)) {
            stream.forEach(source -> {
                try {
                    Path destination = backupDestDir.resolve(sourceDir.relativize(source));
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(destination);
                    } else {
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to copy file: " + source + " -> " + e.getMessage());
                }
            });
        }
        System.out.println("Backup completed successfully at: " + backupDestDir);
    }
    @Override
    public long getDirectorySize(String directoryPath) throws IOException {
        Path dir = Paths.get(directoryPath);
        try (Stream<Path> stream = Files.walk(dir)) {
            return stream
                    .filter(p -> p.toFile().isFile())
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            System.err.println("Could not get size for file: " + p);
                            return 0L;
                        }
                    })
                    .sum();
        }
    }
}