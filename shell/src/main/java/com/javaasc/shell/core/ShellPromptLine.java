package com.javaasc.shell.core;

import java.util.LinkedList;

public class ShellPromptLine {
    private String prompt;
    private LinkedList<Character> text;
    private int cursorPosition;

    public ShellPromptLine(String prompt) {
        this.prompt = prompt;
        text = new LinkedList<>();
        cursorPosition = 0;
    }

    public void add(char c) {
        text.add(cursorPosition, c);
        cursorPosition++;
    }

    public String getCommand() {
        StringBuilder command = new StringBuilder();
        for (Character c : text) {
            command.append(c);
        }
        return command.toString();
    }

    public void clear() {
        text.clear();
        cursorPosition = 0;
    }

    public String getAll() {
        return prompt + getCommand();
    }

    public void right() {
        if (cursorPosition < text.size()) {
            cursorPosition++;
        }
    }

    public void left() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
    }
}
