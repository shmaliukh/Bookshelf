package org.vshmaliukh.services.file_service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@NoArgsConstructor
public abstract class UserFilesHandler {

    public static final String PROGRAM_DIR_NAME = "shelf";

    @Setter
    @Getter
    protected String userName;
    protected String homeDirectoryStr;
    protected String programDirectoryStr;

    protected UserFilesHandler(String homeDir, String userName) {
        this.userName = userName;
        homeDirectoryStr = homeDir;
        programDirectoryStr = String.valueOf(Paths.get(homeDirectoryStr, PROGRAM_DIR_NAME));
    }

    public boolean createDirectoryIfNotExists(Path dir) {
        if (dir == null || dir.toString().equals("")) {
            return false;
        }
        if (dir.toFile().exists()) {
            return true;
        }
        try {
            Files.createDirectories(dir);
        } catch (IOException ioe) {
            informAboutErr("Problem to create directory '" + dir + "'", ioe);
            return false;
        }
        return false;
    }

    public Path generatePathForUser() {
        Path path = Paths.get(programDirectoryStr, userName);
        createDirectoryIfNotExists(path);
        return path;
    }

    protected void informAboutErr(String problemMessage, Exception exception) {
        log.error("[User]: name: '" + userName + "'"
                + " // [FilesHandler]: " + problemMessage
                + " // [Exception]: " + exception.getMessage());
    }
}
