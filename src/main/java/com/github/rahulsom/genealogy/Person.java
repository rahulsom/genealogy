package com.github.rahulsom.genealogy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Created by rahulsomasunderam on 5/31/14. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  /**
   * The person's first name.
   *
   * @param firstName The first name
   * @return The first name
   */
  String firstName;

  /**
   * The person's last name.
   *
   * @param lastName The last name
   * @return The last name
   */
  String lastName;

  /**
   * The person's race.
   *
   * @param race The race
   * @return The race
   */
  Race race;

  /**
   * The person's gender.
   *
   * @param gender The gender
   * @return The gender
   */
  String gender;
}
