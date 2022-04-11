package com.github.imthenico.inject;

import com.github.imthenico.inject.annotation.InjectAll;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class SimpleConstructorModel implements ConstructorModel {

    private final List<ParameterData> parameterData;
    private final Constructor<?> constructor;
    private final InjectAll injectAll;

    SimpleConstructorModel(List<ParameterData> parameterData, Constructor<?> constructor, InjectAll injectAll) {
        this.parameterData = parameterData;
        this.constructor = constructor;
        this.injectAll = injectAll;
    }

    @Override
    public List<ParameterData> getParameters() {
        return new ArrayList<>(parameterData);
    }

    @Override
    public Constructor<?> getConstructor() {
        return constructor;
    }

    @Override
    public @Nullable InjectAll getInjectAll() {
        return injectAll;
    }
}