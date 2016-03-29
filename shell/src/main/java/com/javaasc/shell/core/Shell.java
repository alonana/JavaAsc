package com.javaasc.shell.core;

import com.javaasc.shell.api.ShellConnection;
import com.javaasc.util.JascLogger;

import java.util.LinkedList;

public class Shell {
    private static final JascLogger logger = JascLogger.getLogger(Shell.class);

    private final ShellConnection connector;
    private final Object mutex;
    private boolean running = true;
    private ShellPendingOutput pendingOutput;
    private LinkedList<Character> printedText;
    private int cursorPosition;

    public Shell(ShellConnection connector) {
        this.connector = connector;
        mutex = new Object();
        printedText = new LinkedList<Character>();
        pendingOutput = new ShellPendingOutput();
        ShellReader reader = new ShellReader(this);
        ShellWriter writer = new ShellWriter(this);
        new Thread(reader).start();
        new Thread(writer).start();
    }

    public void handle() throws Exception {
        logger.debug("Shell starting");
        addText("Welcome to JASC\r\n");
        while (running) {
            synchronized (mutex) {
                mutex.wait();
            }
        }
    }

    public void readerThread() {
        logger.debug("reader thread starting");
        try {
            readerThreadWrapped();
        } catch (Throwable e) {
            logger.warn("read failed", e);
        }
        close();
    }

    public void writerThread() {
        logger.debug("writer thread starting");
        try {
            writerThreadWrapped();
        } catch (Throwable e) {
            logger.warn("write failed", e);
        }
        close();
    }

    private void close() {
        logger.debug("closing shell");
        running = false;
        pendingOutput.stop();
        synchronized (mutex) {
            mutex.notifyAll();
        }
    }

    private void writerThreadWrapped() throws Exception {
        while (running) {
            logger.debug("pending output starting");
            String text = pendingOutput.getText();
            logger.debug("pending output done: {}", text);
            if (text != null) {
                connector.getOutputStream().write(text.getBytes());
                connector.getOutputStream().flush();
            }
        }
    }

    public void addText(String text) {
        pendingOutput.addText(text);
    }

    private void readerThreadWrapped() throws Exception {
        while (running) {
            logger.debug("read starting");
            int c = connector.getInputStream().read();
            logger.debug("read done: {}", c);
            if (c == -1) {
                logger.debug("reader EOF");
                return;
            }
            Character character = (char) c;
            printedText.add(cursorPosition, character);
            addText(character.toString());
        }
    }

}
