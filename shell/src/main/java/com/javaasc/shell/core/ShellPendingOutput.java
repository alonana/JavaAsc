package com.javaasc.shell.core;

import com.javaasc.util.JascLogger;

import java.util.LinkedList;

public class ShellPendingOutput {
    private static final JascLogger logger = JascLogger.getLogger(ShellPendingOutput.class);

    private final LinkedList<String> pendingText;
    private final Object mutex;
    private boolean running;

    public ShellPendingOutput() {
        this.pendingText = new LinkedList<String>();
        mutex = new Object();
        running = true;
    }

    public void addText(String text) {
        logger.debug("adding text for print {}", text);
        synchronized (mutex) {
            pendingText.add(text);
            mutex.notifyAll();
        }
    }

    public String getText() {
        while (running) {
            synchronized (mutex) {
                if (!pendingText.isEmpty()) {
                    return pendingText.removeFirst();
                }
                try {
                    mutex.wait(1000);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
        return null;
    }

    public void stop() {
        running = false;
    }
}
