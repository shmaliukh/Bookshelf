package org.vshmaliukh;

import org.vshmaliukh.client.TelnetClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            TelnetClient.main(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
