package com.javaasc.entity.core;

import com.javaasc.entity.api.JascValues;

import java.util.LinkedList;
import java.util.List;

public class EmptyValues implements JascValues {
    @Override
    public List<String> getValues() {
        return new LinkedList<>();
    }
}
