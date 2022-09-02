package org.vshmaliukh.server;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.console_terminal_app.ConsoleUI;

@Slf4j
public class TerminalToClientThread extends Thread {

    private int userCounter;

    private ConsoleUI consoleTerminal;

    public TerminalToClientThread(int userCounter, ConsoleUI consoleTerminal) {
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