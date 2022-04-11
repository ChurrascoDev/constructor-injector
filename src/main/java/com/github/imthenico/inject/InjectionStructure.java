package com.github.imthenico.inject;

import com.github.imthenico.inject.exception.InvocationException;
import com.github.imthenico.inject.extracted.Extracted;

public interface InjectionStructure {

    Extracted[] getExtractedTargets();

    Extracted getByName(String name);

    Class<?> fromType();

    ConstructorModel getConstructorModel();

    InjectionContext getValues(Object source) throws InvocationException;

}