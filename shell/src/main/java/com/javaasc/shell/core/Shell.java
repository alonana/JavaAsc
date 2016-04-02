package com.javaasc.shell.core;

import com.javaasc.shell.api.ShellConnection;
import com.javaasc.util.JascLogger;

public class Shell {
    public static final int BEEP = 7;
    public static final String DELETE_ADDITIONAL = " \b";
    public static final String PROMPT = "JASC>";

    private static final JascLogger logger = JascLogger.getLogger(Shell.class);

    private final ShellConnection connector;
    private final Object mutex;
    private final EscapeHandlingStream inputStream;
    private boolean running = true;
    private boolean closed = false;
    private ShellPendingOutput pendingOutput;
    private ShellPromptLine promptLine;

    public Shell(ShellConnection connector) throws Exception {
        this.connector = connector;
        mutex = new Object();
        promptLine = new ShellPromptLine(PROMPT);
        pendingOutput = new ShellPendingOutput();
        inputStream = new EscapeHandlingStream(connector.getInputStream());

        new Thread(this::readerThread).start();
        new Thread(this::writerThread).start();
    }

    public void waitToComplete() {
        pendingOutput.waitForEmpty();
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

    private void readerThread() {
        try {
            readerThreadWrapped();
        } catch (Throwable e) {
            logger.warn("read failed", e);
        } finally {
            closeIgnored(true);
        }
    }

    private void writerThread() {
        try {
            logger.debug("writer thread starting");
            writerThreadWrapped();
            logger.debug("writer thread done");
        } catch (Throwable e) {
            logger.warn("write failed", e);
        } finally {
            closeIgnored(false);
        }
    }

    private void closeIgnored(boolean onlyNotify) {
        try {
            close(onlyNotify);
        } catch (Exception e) {
            logger.warn("ignoring close error ", e);
        }
    }

    // close with onlyNotify=false is called only once from writerThread
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
        inputStream.close();
        connector.getOutputStream().close();
        connector.getErrorStream().close();
    }

    private void writerThreadWrapped() throws Exception {
        logger.debug("writer thread starting");
        while (running) {
            logger.debug("pending output starting");
            String text = pendingOutput.getText();
            logger.debug("pending output done: {}", text);
            if (text != null) {
                if (text.equals(EscapeHandlingStream.BELL)) {
                    connector.getOutputStream().write(BEEP);
                } else {
                    connector.getOutputStream().write(text.getBytes());
                }
                connector.getOutputStream().flush();
            }
        }
        logger.debug("writer thread done");
    }

    void addText(String text) {
        pendingOutput.addText(text);
    }

    private void readerThreadWrapped() throws Exception {
        logger.debug("reader thread starting");
        while (running) {
            logger.debug("read starting");
            int c = inputStream.read();
            handleReadChar(c);
            // print and delete additional char to delete additional char for backspace handling
            addText("\r" + promptLine.getAll() + DELETE_ADDITIONAL + promptLine.getBackwardString());
        }
        logger.debug("reader thread done");
    }

    private void handleReadChar(int c) throws Exception {
        if (c == -1) {
            logger.debug("reader EOF");
            close(true);
            return;
        }

        if (c == EscapeHandlingStream.BACKSPACE) {
            promptLine.backspace();
            return;
        }
        if (c == EscapeHandlingStream.RIGHT) {
            promptLine.right();
            return;
        }
        if (c == EscapeHandlingStream.LEFT) {
            promptLine.left();
            return;
        }

        if (c == EscapeHandlingStream.TAB) {
            new CompletionManager(this).complete();
            return;
        }
        if (c == EscapeHandlingStream.ENTER) {
            new CommandRunner(promptLine, this).execute();
            return;
        }
        if (c == 'X') {
            addText("BYE\r\n");
            close(true);
            return;
        }

        char character = (char) c;
        promptLine.add(character);
    }

    public ShellPromptLine getPromptLine() {
        return promptLine;
    }
}
