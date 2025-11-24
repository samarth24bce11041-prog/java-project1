package edu.ccrm.io;
import java.io.IOException;
public interface DataPersistenceService {
    void exportData(String directoryPath) throws IOException;
    void importData(String directoryPath) throws IOException;
}