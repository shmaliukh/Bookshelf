package org.vshmaliukh.services.gson_service;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public abstract class FilesHandler {

    public static final String PROGRAM_DIR_NAME = "shelf";

    protected String userName;
    protected String homeDirectoryStr;
    protected String programDirectoryStr;

    protected FilesHandler(String homeDir, String userName) {
        this.userName = userName;
        homeDirectoryStr = homeDir;
        programDirectoryStr = String.valueOf(Paths.get(homeDirectoryStr, PROGRAM_DIR_NAME));
    }

    public boolean createDirectoryIfNotExists(Path dir) {
        if (dir == null || dir.equals("")) {
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

    protected Path generatePathForUser() {
        Path path = Paths.get(programDirectoryStr, userName);
        createDirectoryIfNotExists(path);
        return path;
    }

    protected void informAboutErr(String problemMessage, Exception exception) {
        log.error("[User]: name: '" + userName + "'"
                + " // [FilesHandler]: " + problemMessage
                + " // [Exception]: " + exception.getMessage()); // TODO is ok to use .getMessage ???
    }
}
