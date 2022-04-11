package com.github.imthenico.inject.extracted;

import com.github.imthenico.inject.exception.InvocationException;

public interface Extracted {

    String name();

    Object getValue(Object container) throws InvocationException;

    static Extracted fromMethod(String methodName, Class<?> from, Class<?>... argTypes) throws Exception {
        return new ExtractedMethod(from.getDeclaredMethod(methodName, argTypes));
    }

    static Extracted fromField(String fieldName, Class<?> from) throws Exception {
        return new ExtractedField(from.getDeclaredField(fieldName));
    }
}