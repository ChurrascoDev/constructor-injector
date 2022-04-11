package com.github.imthenico.inject;

import org.jetbrains.annotations.Nullable;

public class ParameterData {

    private final boolean extractFromMethod;
    private final boolean manualInject;
    private final int paramIndex;
    private final String targetName;

    public ParameterData(boolean extractFromMethod, boolean manualInject, int paramIndex, String targetName) {
        this.extractFromMethod = extractFromMethod;
        this.manualInject = manualInject;
        this.paramIndex = paramIndex;
        this.targetName = targetName;
    }

    public boolean isExtractFromMethod() {
        return extractFromMethod;
    }

    public boolean isManualInject() {
        return manualInject;
    }

    public @Nullable String getTargetName() {
        return targetName;
    }

    public int getParamIndex() {
        return paramIndex;
    }
}