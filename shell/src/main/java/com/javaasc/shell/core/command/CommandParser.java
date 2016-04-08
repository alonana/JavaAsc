package com.javaasc.shell.core.command;

import com.javaasc.entity.core.Arguments;
import com.javaasc.util.JascException;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class CommandParser {
    private LinkedList<String> rawArguments;

    public CommandParser(String commandLine, boolean completion) throws Exception {
        rawArguments = new LinkedList<>();
        if (commandLine == null || commandLine.length() == 0) {
            if (completion) {
                return;
            }
            throw new JascException("empty command line");
        }

        CommandParserState state = CommandParserState.NORMAL;
        StringTokenizer tokenizer = new StringTokenizer(commandLine, "\"'\\ ", true);
        StringBuilder current = new StringBuilder();
        boolean lastTokenHasBeenQuoted = false;

        while (tokenizer.hasMoreTokens()) {
            String nextTok = tokenizer.nextToken();
            switch (state) {
                case IN_QUOTE:
                    if ("\\".equals(nextTok)) {
                        state = CommandParserState.IN_QUOTE_ESCAPED;
                    } else if ("\'".equals(nextTok)) {
                        lastTokenHasBeenQuoted = true;
                        state = CommandParserState.NORMAL;
                    } else {
                        current.append(nextTok);
                    }
                    break;
                case IN_QUOTE_ESCAPED:
                    current.append(nextTok);
                    state = CommandParserState.IN_QUOTE;
                    break;
                case IN_DOUBLE_QUOTE:
                    if ("\\".equals(nextTok)) {
                        state = CommandParserState.IN_DOUBLE_QUOTE_ESCAPED;
                    } else if ("\"".equals(nextTok)) {
                        lastTokenHasBeenQuoted = true;
                        state = CommandParserState.NORMAL;
                    } else {
                        current.append(nextTok);
                    }
                    break;
                case IN_DOUBLE_QUOTE_ESCAPED:
                    current.append(nextTok);
                    state = CommandParserState.IN_DOUBLE_QUOTE;
                    break;
                default:
                    if ("\'".equals(nextTok)) {
                        state = CommandParserState.IN_QUOTE;
                    } else if ("\"".equals(nextTok)) {
                        state = CommandParserState.IN_DOUBLE_QUOTE;
                    } else if (" ".equals(nextTok)) {
                        if (lastTokenHasBeenQuoted || current.length() != 0) {
                            rawArguments.add(current.toString());
                            current.setLength(0);
                        }
                    } else {
                        current.append(nextTok);
                    }
                    lastTokenHasBeenQuoted = false;
                    break;
            }
        }
        if (lastTokenHasBeenQuoted || current.length() != 0) {
            rawArguments.add(current.toString());
        }
        if (state == CommandParserState.IN_QUOTE || state == CommandParserState.IN_DOUBLE_QUOTE) {
            if (completion) {
                return;
            }
            throw new JascException("quotes error for " + commandLine);
        }
    }

    public LinkedList<String> getRawArguments() {
        return rawArguments;
    }

    public Arguments getArguments() throws Exception {
        return new Arguments(rawArguments, true);
    }

    @Override
    public String toString() {
        return "CommandParser{" +
                "rawArguments=" + rawArguments +
                '}';
    }

}
