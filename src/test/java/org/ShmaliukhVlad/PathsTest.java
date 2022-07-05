package org.ShmaliukhVlad;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class PathsTest {

    @Test
    public void test() {
        System.out.println(Paths.get(System.getProperty("user.home"), "dir1", "dir2"));
    }
}
