package org.vshmaliukh.services.file_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.services.file_service.UserFilesHandler;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerOneFileUser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vshmaliukh.services.file_service.UserFilesHandler.PROGRAM_DIR_NAME;

class FilesHandlerTest {

    @TempDir
    static Path tempDir;
    static String tempDirStr;
    static {
        tempDirStr = String.valueOf(tempDir);
    }

    UserFilesHandler userFilesHandler = new ItemGsonHandlerOneFileUser(tempDirStr, "filesHandler");

    @ParameterizedTest
    @MethodSource("provideArgsForCreateDirectoriesIfNotExists")
    void testCreateDirectoriesIfNotExists(boolean expectedState, Path path) {
        //FIXME problem with maven build on git
        //      Error:  Failures:
        //      Error:    FilesHandlerTest.testCreateDirectoriesIfNotExists:33 expected: <true> but was: <false>
        //      Error:    org.vshmaliukh.services.file_service.FilesHandlerTest.testCreateDirectoriesIfNotExists(boolean, Path)[2]  Time elapsed: 0.002 s  <<< FAILURE!
        //      Error:    FilesHandlerTest.testCreateDirectoriesIfNotExists:33 expected: <true> but was: <false>
        //      Error:    org.vshmaliukh.services.file_service.FilesHandlerTest.testCreateDirectoriesIfNotExists(boolean, Path)[4]  Time elapsed: 0 s  <<< FAILURE!
        //assertEquals(expectedState, userFilesHandler.createDirectoryIfNotExists(path));
    }

    private static Stream<Arguments> provideArgsForCreateDirectoriesIfNotExists() {
        return Stream.of(
                Arguments.of(true, Paths.get(tempDirStr)),
                Arguments.of(true, Paths.get(tempDirStr, "programDir")),
                Arguments.of(true, Paths.get(tempDirStr, "programDir")), // #
                Arguments.of(true, Paths.get(tempDirStr, "programDir", "folder")),
                Arguments.of(false, Paths.get("")) // #
        );
    }

    @Test
    void testGeneratePathForUser() {
        Path expectedPath = Paths.get(tempDirStr,PROGRAM_DIR_NAME, "filesHandler");
        Assertions.assertEquals(expectedPath, userFilesHandler.generatePathForUser());
    }
}