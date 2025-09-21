package com.github.rahulsom.genealogy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rahulsomasunderam on 5/31/14.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    String firstName;
    String lastName;
    Race race;
    String gender;
}
