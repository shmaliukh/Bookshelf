package org.vshmaliukh.client_server_model.server.server_threads;

import org.vshmaliukh.Terminal;

public class TerminalToClientThread extends Thread{

    private int terminalConfig;
    private int userCounter;

    private Terminal terminal;

    public TerminalToClientThread(int userCounter, Terminal terminal , int terminalConfig) {
        this.terminalConfig = terminalConfig;
        this.userCounter = userCounter;
        this.terminal = terminal;
    }

    @Override
    public void run(){
        System.out.println("    [Client] " + this.userCounter +" - start");
        try {
            terminal.startWork(terminalConfig, true);
        } catch (Exception e) {
            System.err.println("Problem to start terminal");
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("    [Client] " + this.userCounter +" - stop");
        }
    }
}