package com.javaasc.shell.core;

import com.javaasc.util.JascLogger;

import java.util.LinkedList;

public class ShellPendingOutput {
    public static final int WAIT_TIMEOUT = 100;
    private static final JascLogger logger = JascLogger.getLogger(ShellPendingOutput.class);
    private final LinkedList<String> pendingText;
    private final Object mutex;
    private boolean running;

    ShellPendingOutput() {
        this.pendingText = new LinkedList<>();
        mutex = new Object();
        running = true;
    }

    void addText(String text) {
        logger.debug("adding text for print {}", text);
        synchronized (mutex) {
            pendingText.add(text);
            mutex.notifyAll();
        }
    }

    String getText() {
        while (running) {
            synchronized (mutex) {
                if (!pendingText.isEmpty()) {
                    return pendingText.removeFirst();
                }
                try {
                    mutex.wait(WAIT_TIMEOUT);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
        return null;
    }

    public void waitForEmpty() {
        while (running) {
            synchronized (mutex) {
                if (pendingText.isEmpty()) {
                    return;
                }
                try {
                    mutex.wait(WAIT_TIMEOUT);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
    }

    void stop() {
        running = false;
    }
}
