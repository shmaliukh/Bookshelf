package org.vshmaliukh.services.gson_service;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

@Slf4j
public abstract class FilesHandler {

    public static final String PROGRAM_DIR_NAME = "shelf";


    String homeDirectoryStr;
    String programDirectoryStr;

    public FilesHandler(String homeDir) {
        homeDirectoryStr = homeDir;
        programDirectoryStr = String.valueOf(Paths.get(homeDirectoryStr, PROGRAM_DIR_NAME));
    }
}
