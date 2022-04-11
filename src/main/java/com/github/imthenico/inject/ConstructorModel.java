package com.github.imthenico.inject;

import com.github.imthenico.inject.annotation.InjectAll;
import com.github.imthenico.inject.annotation.Skip;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public interface ConstructorModel {

    List<ParameterData> getParameters();

    Constructor<?> getConstructor();

    @Nullable InjectAll getInjectAll();

    static ConstructorModel from(Constructor<?> constructor) {
        InjectAll injectAll = constructor.getAnnotation(InjectAll.class);
        List<ParameterData> parameterData = new ArrayList<>();

        int toExtractIndex = 0;
        int paramIndex = 0;
        String[] valuesToExtract = injectAll.valuesToExtract();
        for (Parameter parameter : constructor.getParameters()) {
            try {
                ParameterData data;

                if (parameter.isAnnotationPresent(Skip.class)) {
                    data = new ParameterData(false, true, paramIndex, parameter.getName());
                } else {
                    String name = valuesToExtract[toExtractIndex];

                    boolean extractFromMethod = name.endsWith("()");

                    if (extractFromMethod)
                        name = name.substring(0, name.length() - 2);

                    data = new ParameterData(extractFromMethod, false, paramIndex, name);
                    toExtractIndex++;
                }

                parameterData.add(data);
            } catch (IndexOutOfBoundsException e) {
                throw new RuntimeException(
                        format("param %s in " + constructor + " is not configured (extract value or use @Skip)", paramIndex),  e);
            }
            paramIndex++;
        }

        return new SimpleConstructorModel(parameterData, constructor, injectAll);
    }

    static ConstructorModel fromClass(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(InjectAll.class))
                return from(constructor);
        }

        throw new IllegalArgumentException("No constructor found with @InjectAll annotation");
    }
}