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
    private final EscapeHandlingStream inputStream;

    public Shell(ShellConnection connector) throws Exception {
        this.connector = connector;
        mutex = new Object();
        promptLine = new ShellPromptLine("JASC>");
        pendingOutput = new ShellPendingOutput();
        inputStream = new EscapeHandlingStream(connector.getInputStream());
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
            readerThreadWrapped();
        } catch (Throwable e) {
            logger.warn("read failed", e);
        } finally {
            closeIgnored(true);
        }
    }

    void writerThread() {
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
                if (text.equals(EscapeHandlingStream.BEEP)) {
                    connector.getOutputStream().write(7);
                } else {
                    connector.getOutputStream().write(text.getBytes());
                }
                connector.getOutputStream().flush();
            }
        }
        logger.debug("writer thread done");
    }

    public void addText(String text) {
        pendingOutput.addText(text);
    }

    private void readerThreadWrapped() throws Exception {
        logger.debug("reader thread starting");
        while (running) {
            logger.debug("read starting");
            int c = inputStream.read();
            handleReadChar(c);
            addText("\r" + promptLine.getAll() + promptLine.getBackwardString());
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
            new CompletionManager(promptLine, this).complete();
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

}
