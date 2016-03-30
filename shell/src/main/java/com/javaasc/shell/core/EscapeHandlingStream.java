package com.javaasc.shell.core;

import com.javaasc.util.JascLogger;

import java.io.IOException;
import java.io.InputStream;

public class EscapeHandlingStream {
    private static final JascLogger logger = JascLogger.getLogger(EscapeHandlingStream.class);

    public static final int BACKSPACE = 1303;
    public static final int RIGHT = 1003;
    public static final int LEFT = 1004;
    public static final int TAB = 1301;
    public static final int ENTER = 10;
    public static final String BEEP = "\uD83D\uDD14";

    private InputStream inputStream;


    public EscapeHandlingStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void close() throws Exception {
        inputStream.close();
    }

    public int read() throws Exception {
        int c = getCharAndLog();

        if (c == 27) {
            c = getCharAndLog();
            if (c == 91) {
                c = getCharAndLog();
                if (c == 67) {
                    return RIGHT;
                }
                if (c == 68) {
                    return LEFT;
                }
            }
        }
        if (c == '\t') {
            return TAB;
        }
        if (c == '\n' || c == '\r') {
            return ENTER;
        }
        return c;
    }

    private int getCharAndLog() throws IOException {
        int c = inputStream.read();
        logger.debug("read done: int {} char {}", c, (char) c);
        return c;
    }
}
