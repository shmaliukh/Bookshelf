package org.vshmaliukh;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class GetChildePathsTest {
    @Test
    public void test() throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), "book_shelf", "no_user");
        Iterator<Path> iterator = path.iterator();
//        Files.list(path).filter( p-> set.contains(p.getFileName()));
    }
}
