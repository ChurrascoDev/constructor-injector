package com.github.imthenico.inject.extracted;

import com.github.imthenico.inject.exception.InvocationException;

import java.lang.reflect.Field;

public class ExtractedField implements Extracted {

    private final Field field;

    ExtractedField(Field field) {
        this.field = field;
    }

    @Override
    public String name() {
        return field.getName();
    }

    @Override
    public Object getValue(Object container) throws InvocationException {
        try {
            boolean oldAccessible = field.isAccessible();
            field.setAccessible(true);
            Object value = field.get(container);
            field.setAccessible(oldAccessible);

            return value;
        } catch (IllegalAccessException e) {
            throw new InvocationException(e);
        }
    }
}
