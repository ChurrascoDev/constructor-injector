package com.github.imthenico.inject;

import com.github.imthenico.inject.annotation.InjectAll;

import java.lang.reflect.InvocationTargetException;

public interface InjectionHandler {

    static InjectionHandler create() {
        return new InjectionHandlerImpl();
    }

    InjectionStructure createStructure(
            Class<?> from,
            ConstructorModel constructorModel
    );

    default InjectionStructure createStructure(ConstructorModel constructorModel) {
        InjectAll injectAll = constructorModel.getInjectAll();

        if (injectAll == null) {
            throw new IllegalArgumentException("No @InjectAll such in constructor model");
        }

        if (injectAll.from().equals(Class.class)) {
            throw new IllegalArgumentException("No source class specified in @InjectAll");
        }

        return createStructure(injectAll.from(), constructorModel);
    }

    <T> T createInstance(InjectionContext injectionContext)
            throws InvocationTargetException, InstantiationException, IllegalAccessException;

}