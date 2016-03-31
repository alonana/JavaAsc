package com.javaasc.shell.core;

import java.util.Iterator;
import java.util.LinkedList;

public class ShellPromptLine {
    private String prompt;
    private LinkedList<Character> text;
    private int cursorPosition;

    ShellPromptLine(String prompt) {
        this.prompt = prompt;
        text = new LinkedList<>();
        cursorPosition = 0;
    }

    void add(String s) {
        for (char c : s.toCharArray()) {
            add(c);
        }
    }

    void add(char c) {
        text.add(cursorPosition, c);
        cursorPosition++;
    }

    String getCommandLine() {
        StringBuilder command = new StringBuilder();
        for (Character c : text) {
            command.append(c);
        }
        return command.toString();
    }

    String getCommandUntilCursor() {
        StringBuilder command = new StringBuilder();
        Iterator<Character> iterator = text.iterator();
        for (int i = 0; i < cursorPosition; i++) {
            command.append(iterator.next());
        }
        return command.toString();
    }

    void clear() {
        text.clear();
        cursorPosition = 0;
    }

    public String getAll() {
        return prompt + getCommandLine();
    }

    void right() {
        if (cursorPosition < text.size()) {
            cursorPosition++;
        }
    }

    void left() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
    }

    void backspace() {
        if (cursorPosition > 0) {
            cursorPosition--;
            text.remove(cursorPosition);
        }
    }

    String getBackwardString() {
        StringBuilder back = new StringBuilder();
        for (int i = text.size(); i > cursorPosition; i--) {
            back.append('\b');
        }
        return back.toString();
    }

    public String getPrompt() {
        return prompt;
    }
}
