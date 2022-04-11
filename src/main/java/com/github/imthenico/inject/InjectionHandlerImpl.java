package com.github.imthenico.inject;

import com.github.imthenico.inject.extracted.Extracted;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InjectionHandlerImpl implements InjectionHandler {

    @Override
    public InjectionStructure createStructure(Class<?> from, ConstructorModel constructorModel) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(constructorModel);

        List<Extracted> extracteds = new ArrayList<>();

        for (ParameterData parameter : constructorModel.getParameters()) {
            if (parameter.isManualInject()) {
                continue;
            }

            String extractName = parameter.getTargetName();

            Extracted extracted;

            try {
                if (parameter.isExtractFromMethod()) {
                    extracted = Extracted.fromMethod(extractName, from);
                } else {
                    extracted = Extracted.fromField(extractName, from);
                }

                extracteds.add(extracted);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new SimpleInjectionStructure(extracteds.toArray(new Extracted[0]), from, constructorModel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T createInstance(InjectionContext injectionContext) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = (Constructor<T>) injectionContext.getInjectionStructure()
                .getConstructorModel()
                .getConstructor();

        List<Object> values = new ArrayList<>(constructor.getParameterCount());
        for (int i = 0; i < constructor.getParameterCount(); i++) {
            values.add(injectionContext.get(i));
        }

        return constructor.newInstance(values.toArray());
    }
}