package com.github.rahulsom.genealogy;

/**
 * Represents any kind of name
 */
public class Name {
    private String value;
    private double cumulativeProbability;

    public String getValue() {
        return value;
    }

    public double getCumulativeProbability() {
        return cumulativeProbability;
    }

    public Name(String value, double cumulativeProbability) {
        this.value = value;
        this.cumulativeProbability = cumulativeProbability;
    }

}
