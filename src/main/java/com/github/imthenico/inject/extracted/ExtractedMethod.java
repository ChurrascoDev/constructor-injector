package com.github.imthenico.inject.extracted;

import com.github.imthenico.inject.exception.InvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExtractedMethod implements Extracted {

    private final Method method;

    ExtractedMethod(Method method) {
        this.method = method;
    }

    @Override
    public String name() {
        return method.getName();
    }

    @Override
    public Object getValue(Object container) throws InvocationException {
        try {
            boolean oldAccessible = method.isAccessible();
            method.setAccessible(true);
            Object value = method.invoke(container);
            method.setAccessible(oldAccessible);

            return value;
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new InvocationException(e);
        }
    }
}
