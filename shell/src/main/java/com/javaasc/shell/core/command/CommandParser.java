package com.javaasc.shell.core.command;

import com.javaasc.entity.com.javaasc.entity.core.Arguments;
import com.javaasc.util.JascException;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CommandParser {
    private Arguments arguments;

    public CommandParser(String commandLine) throws Exception {
        if (commandLine == null || commandLine.length() == 0) {
            throw new JascException("empty command line");
        }

        CommandParserState state = CommandParserState.NORMAL;
        StringTokenizer tokenizer = new StringTokenizer(commandLine, "\"'\\ ", true);
        ArrayList<String> result = new ArrayList<>();
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
                            result.add(current.toString());
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
            result.add(current.toString());
        }
        if (state == CommandParserState.IN_QUOTE || state == CommandParserState.IN_DOUBLE_QUOTE) {
            throw new JascException("quotes error for " + commandLine);
        }
        arguments = new Arguments(result);
    }

    @Override
    public String toString() {
        return "CommandParser{" +
                "arguments=" + arguments +
                '}';
    }

    public Arguments getArguments() {
        return arguments;
    }

}
