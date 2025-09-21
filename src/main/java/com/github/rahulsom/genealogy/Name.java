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
    private String value;
    private double cumulativeProbability;

}
