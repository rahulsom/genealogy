package com.github.rahulsom.genealogy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Represents any kind of name */
@Getter
@RequiredArgsConstructor
public class Name {
  /** The name. */
  private final String value;

  /** The cumulative probability of the name. */
  private final double cumulativeProbability;
}
