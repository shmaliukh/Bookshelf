package org.vshmaliukh.services.save_read_services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.services.save_read_services.gson_handler.ItemGsonHandlerOneFileForUser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vshmaliukh.services.save_read_services.SaveReadUserFilesHandler.PROGRAM_DIR_NAME;

class FilesHandlerTest {

    @TempDir
    static Path tempDir;
    static String tempDirStr;
    static {
        tempDirStr = String.valueOf(tempDir);
    }

    SaveReadUserFilesHandler userFilesHandler = new ItemGsonHandlerOneFileForUser("filesHandler");

    @ParameterizedTest
    @MethodSource("provideArgsForCreateDirectoriesIfNotExists")
    void testCreateDirectoriesIfNotExists(boolean expectedState, Path path) {
        assertEquals(expectedState, userFilesHandler.createDirectoryIfNotExists(path, "test"));
    }

    private static Stream<Arguments> provideArgsForCreateDirectoriesIfNotExists() {
        return Stream.of(
                Arguments.of(true, Paths.get(tempDirStr)),
                Arguments.of(true, Paths.get(tempDirStr, "programDir")),
                Arguments.of(true, Paths.get(tempDirStr, "programDir", "folder"))
        );
    }

    @Test
    void testGeneratePathForUser() {
        Path expectedPath = Paths.get(tempDirStr,PROGRAM_DIR_NAME, "filesHandler");
        assertEquals(expectedPath, userFilesHandler.generatePathForUser(tempDirStr, "test"));
    }
}