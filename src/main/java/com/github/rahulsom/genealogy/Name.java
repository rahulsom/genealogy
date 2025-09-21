package com.github.rahulsom.genealogy;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Represents any kind of name
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Name {
    /**
     * The name.
     * @param value the value
     * @return the value
     */
    private String value;
    /**
     * The cumulative probability of the name.
     * @param cumulativeProbability the cumulative probability
     * @return the cumulative probability
     */
    private double cumulativeProbability;

}
