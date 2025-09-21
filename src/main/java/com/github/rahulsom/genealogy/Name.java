package com.github.rahulsom.genealogy;

import java.util.Objects;

/**
 * Represents any kind of name.
 */
public class Name {
    /**
     * The name.
     */
    private String value;
    /**
     * The cumulative probability of the name.
     */
    private double cumulativeProbability;

    /**
     * Default constructor.
     */
    public Name() {
    }

    /**
     * Creates a name.
     *
     * @param value                 The name
     * @param cumulativeProbability The cumulative probability
     */
    public Name(String value, double cumulativeProbability) {
        this.value = value;
        this.cumulativeProbability = cumulativeProbability;
    }

    /**
     * Gets the name.
     *
     * @return The name
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the name.
     *
     * @param value The name
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the cumulative probability.
     *
     * @return The cumulative probability
     */
    public double getCumulativeProbability() {
        return cumulativeProbability;
    }

    /**
     * Sets the cumulative probability.
     *
     * @param cumulativeProbability The cumulative probability
     */
    public void setCumulativeProbability(double cumulativeProbability) {
        this.cumulativeProbability = cumulativeProbability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Double.compare(name.cumulativeProbability, cumulativeProbability) == 0 &&
                Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, cumulativeProbability);
    }

    @Override
    public String toString() {
        return "Name{" +
                "value='" + value + '\'' +
                ", cumulativeProbability=" + cumulativeProbability +
                '}';
    }
}
