package com.javaasc.shell.core;

import com.javaasc.shell.api.ShellConnection;
import com.javaasc.util.JascLogger;

public class Shell {
    private static final JascLogger logger = JascLogger.getLogger(Shell.class);

    private final ShellConnection connector;
    private final Object mutex;
    private boolean running = true;
    private boolean closed = false;
    private ShellPendingOutput pendingOutput;
    private ShellPromptLine promptLine;

    public Shell(ShellConnection connector) {
        this.connector = connector;
        mutex = new Object();
        promptLine = new ShellPromptLine("JASC>");
        pendingOutput = new ShellPendingOutput();
        ShellReader reader = new ShellReader(this);
        ShellWriter writer = new ShellWriter(this);
        new Thread(reader).start();
        new Thread(writer).start();
    }

    public void handle() throws Exception {
        logger.debug("Shell starting");
        addText("Welcome to JASC\r\n");
        addText(promptLine.getAll());
        while (running) {
            synchronized (mutex) {
                mutex.wait();
            }
        }
    }

    void readerThread() {
        try {
            logger.debug("reader thread starting");
            readerThreadWrapped();
            logger.debug("reader thread done");
            close(true);
        } catch (Throwable e) {
            logger.warn("read failed", e);
        }
    }

    void writerThread() {
        try {
            logger.debug("writer thread starting");
            writerThreadWrapped();
            logger.debug("writer thread done");
            close(false);
        } catch (Throwable e) {
            logger.warn("write failed", e);
        }
    }

    private void close(boolean onlyNotify) throws Exception {
        running = false;
        pendingOutput.stop();
        synchronized (mutex) {
            mutex.notifyAll();
        }
        if (onlyNotify) {
            return;
        }
        if (closed) {
            logger.debug("shell already closed");
            return;
        }
        closed = true;
        logger.debug("closing shell");
        connector.getInputStream().close();
        connector.getOutputStream().close();
        connector.getErrorStream().close();
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

    private void addText(String text) {
        pendingOutput.addText(text);
    }

    private void readerThreadWrapped() throws Exception {
        while (running) {
            logger.debug("read starting");
            int c = connector.getInputStream().read();
            handleReadChar(c);
        }
    }

    private void handleReadChar(int c) throws Exception {
        logger.debug("read done: int {} char {}", c, (char) c);
        if (c == -1) {
            logger.debug("reader EOF");
            close(true);
            return;
        }

        if (c == 1003) {
            promptLine.right();
            return;
        }
        if (c == 1004) {
            promptLine.left();
            return;
        }

        if (c == 1301 || c == '\t') {
            addText("\r\n");
            String command = promptLine.getCommand();
            addText("complete in progress for: " + command + "\r\n");
            addText(promptLine.getAll());
            return;
        }
        if (c == '\n' || c == '\r') {
            addText("\r\n");
            String command = promptLine.getCommand();
            promptLine.clear();
            addText("running command: " + command + "\r\n");
            addText(promptLine.getAll());
            return;
        }
        if (c == 'X') {
            addText("BYE\r\n");
            close(true);
            return;
        }

        char character = (char) c;
        promptLine.add(character);
        addText(Character.toString(character));
    }

}
