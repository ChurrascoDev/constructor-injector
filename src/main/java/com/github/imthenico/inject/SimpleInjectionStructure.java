package com.github.imthenico.inject;

import com.github.imthenico.inject.exception.InvocationException;
import com.github.imthenico.inject.extracted.Extracted;

public class SimpleInjectionStructure implements InjectionStructure {

    private final Extracted[] extracteds;
    private final Class<?> fromType;
    private final ConstructorModel constructorModel;

    SimpleInjectionStructure(Extracted[] extracteds, Class<?> fromType, ConstructorModel constructorModel) {
        this.extracteds = extracteds;
        this.fromType = fromType;
        this.constructorModel = constructorModel;
    }

    @Override
    public Extracted[] getExtractedTargets() {
        return extracteds;
    }

    @Override
    public Extracted getByName(String name) {
        for (Extracted extracted : extracteds) {
            if (extracted.name().equals(name)) {
                return extracted;
            }
        }

        return null;
    }

    @Override
    public Class<?> fromType() {
        return fromType;
    }

    @Override
    public ConstructorModel getConstructorModel() {
        return constructorModel;
    }

    @Override
    public InjectionContext getValues(Object source) throws InvocationException {
        if (!fromType.isInstance(source)) {
            throw new IllegalArgumentException(source + " is not an instance of " + fromType);
        }

        InjectionContext context = new InjectionContext(this);

        for (ParameterData parameter : constructorModel.getParameters()) {
            if (!parameter.isManualInject()) {
                Extracted extracted = getByName(parameter.getTargetName());

                context.bind(parameter.getParamIndex(), extracted.getValue(source));
            }
        }

        return context;
    }
}