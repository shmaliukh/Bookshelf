package org.vshmaliukh.client_server.server;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.terminal.Terminal;

@Slf4j
public class TerminalToClientThread extends Thread {

    private int userCounter;

    private Terminal terminal;

    public TerminalToClientThread(int userCounter, Terminal terminal) {
        this.userCounter = userCounter;
        this.terminal = terminal;
    }

    @Override
    public void run() {
        log.info("[Terminal]: #" + this.userCounter + " - start");
        try {
            terminal.startWork(true);
        } catch (Exception e) {
            log.error("[TerminalToClientThread]: Problem to start terminal. Exception: " + e);
        } finally {
            log.info("[Terminal]: #" + this.userCounter + " - stop");
        }
    }
}