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

    public void setValue(String value) {
        this.value = value;
    }

    public double getCumulativeProbability() {
        return cumulativeProbability;
    }

    public void setCumulativeProbability(double cumulativeProbability) {
        this.cumulativeProbability = cumulativeProbability;
    }

    public Name(String value, double cumulativeProbability) {
        this.value = value;
        this.cumulativeProbability = cumulativeProbability;
    }

}
