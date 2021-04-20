package com.tietoevry.quarkus.resteasy.problem.deployment;

import java.util.function.BooleanSupplier;

class ClasspathDetector implements BooleanSupplier {

    private final String className;

    ClasspathDetector(String className) {
        this.className = className;
    }

    @Override
    public boolean getAsBoolean() {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
