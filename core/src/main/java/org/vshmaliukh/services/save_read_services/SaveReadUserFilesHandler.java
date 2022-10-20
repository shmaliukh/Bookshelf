package org.vshmaliukh.services.save_read_services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.vshmaliukh.services.save_read_services.AbstractSaveReadService.*;

public interface SaveReadUserFilesHandler {

    String PROGRAM_DIR_NAME = "shelf";

    String generateFullFileName();

    Path generatePathForFileHandler();

    default boolean createDirectoryIfNotExists(Path dir, String userName) {
        if (dir == null || dir.toString().equals("")) {
            return false;
        }
        if (dir.toFile().exists()) {
            return true;
        }
        try {
            Files.createDirectories(dir);
        } catch (IOException ioe) {
            informAboutErr(userName, "Problem to create directory '" + dir + "'", ioe);
            return false;
        }
        return false;
    }

    default Path generatePathForUser(String homeProperty, String userName) {
        String programDirectoryStr = String.valueOf(Paths.get(homeProperty, PROGRAM_DIR_NAME));
        Path path = Paths.get(programDirectoryStr, userName);
        createDirectoryIfNotExists(path, userName);
        return path;
    }

}
