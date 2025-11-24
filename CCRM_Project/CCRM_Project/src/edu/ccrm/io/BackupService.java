package edu.ccrm.io;
import java.io.IOException;
public interface BackupService {
    void performBackup(String sourceDirectoryPath, String backupRootDirectoryPath) throws IOException;
    long getDirectorySize(String directoryPath) throws IOException;
}