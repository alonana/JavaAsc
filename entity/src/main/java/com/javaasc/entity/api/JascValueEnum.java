package com.javaasc.entity.api;

import java.util.LinkedList;
import java.util.List;

public class JascValueEnum<E extends Enum<E>> implements JascValues {
    private Class<E> enumClass;

    public JascValueEnum(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public List<String> getValues() {
        LinkedList<String> result = new LinkedList<>();
        for (E e : enumClass.getEnumConstants()) {
            result.add(e.toString());
        }
        return result;
    }
}
