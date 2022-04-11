package com.github.imthenico.inject;

import java.util.HashMap;
import java.util.Map;

public class InjectionContext {

    private final Map<Integer, Object> values;
    private final InjectionStructure injectionStructure;

    public InjectionContext(InjectionStructure injectionStructure) {
        this.injectionStructure = injectionStructure;
        this.values = new HashMap<>();
    }

    public Object get(int paramIndex) {
        return values.get(paramIndex);
    }

    public InjectionContext bind(int paramIndex, Object value) {
        values.put(paramIndex, value);
        return this;
    }

    public InjectionStructure getInjectionStructure() {
        return injectionStructure;
    }
}