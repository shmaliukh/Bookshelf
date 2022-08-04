package org.vshmaliukh.client_server.server;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.terminal.ConsoleTerminal;

@Slf4j
public class TerminalToClientThread extends Thread {

    private int userCounter;

    private ConsoleTerminal consoleTerminal;

    public TerminalToClientThread(int userCounter, ConsoleTerminal consoleTerminal) {
        this.userCounter = userCounter;
        this.consoleTerminal = consoleTerminal;
    }

    @Override
    public void run() {
        log.info("[Terminal]: #" + this.userCounter + " - start");
        try {
            consoleTerminal.startWork(true);
        } catch (Exception e) {
            log.error("[TerminalToClientThread]: Problem to start terminal. Exception: " + e);
        } finally {
            log.info("[Terminal]: #" + this.userCounter + " - stop");
        }
    }
}