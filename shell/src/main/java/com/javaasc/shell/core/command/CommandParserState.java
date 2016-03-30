package com.javaasc.shell.core.command;

public enum CommandParserState {
    NORMAL,
    IN_QUOTE,
    IN_QUOTE_ESCAPED,
    IN_DOUBLE_QUOTE,
    IN_DOUBLE_QUOTE_ESCAPED
}
