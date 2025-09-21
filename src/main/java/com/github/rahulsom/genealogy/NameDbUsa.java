package com.github.rahulsom.genealogy;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/** Util to generate names based on US Census data */
public class NameDbUsa {

  private final DataUtil dataUtil;

  private final Random random;
  private static final Random secureRandom = new SecureRandom();

  /**
   * Creates an instance of NameDbUsa.
   *
   * @param random A random number generator.
   */
  public NameDbUsa(Random random) {
    this.random = random;
    dataUtil = DataUtil.getInstance();
  }

  /**
   * Gets an instance of NameDbUsa.
   *
   * @return An instance of NameDbUsa.
   */
  public static NameDbUsa getInstance() {
    return new NameDbUsa(new Random(new SecureRandom().nextLong()));
  }

  /**
   * Finds the max cumulative probability that can be passed for a given list
   *
   * @param list the list
   * @return the max cumulative probability
   */
  private double getMax(List<? extends Name> list) {
    return list.getLast().getCumulativeProbability();
  }

  /**
   * Gets a male name for a given probability.
   *
   * @param probability the probability
   * @return the male name
   */
  public String getMaleName(double probability) {
    return getName(dataUtil.getMaleNames(), probability);
  }

  /**
   * Gets a random male name.
   *
   * @return the male name
   */
  public String getMaleName() {
    double probability = random.nextDouble();
    return getMaleName(probability);
  }

  /**
   * Gets a female name for a given probability.
   *
   * @param probability the probability
   * @return the female name
   */
  public String getFemaleName(double probability) {
    return getName(dataUtil.getFemaleNames(), probability);
  }

  /**
   * Gets a random female name.
   *
   * @return the female name
   */
  public String getFemaleName() {
    double probability = random.nextDouble();
    return getFemaleName(probability);
  }

  /**
   * Gets a last name for a given probability.
   *
   * @param probability the probability
   * @return the last name
   */
  public String getLastName(double probability) {
    return getName(dataUtil.getLastNames(), probability);
  }

  /**
   * Gets a random last name.
   *
   * @return the last name
   */
  public String getLastName() {
    double probability = random.nextDouble();
    return getLastName(probability);
  }

  /** Comparator to find the name at a given cumulative probability */
  private final Comparator<Name> locateNameByCumulativeProbability =
      Comparator.comparingDouble(Name::getCumulativeProbability);

  /**
   * Finds name at a given cumulative probability accounting for gaps.
   *
   * @param list The list to look for name in
   * @param probability the cumulative probability to search for (between 0 and 1)
   * @return the name
   */
  private String getName(List<? extends Name> list, double probability) {
    Name name = getNameObject(list, probability * getMax(list));
    return name.getValue();
  }

  /**
   * Finds name at a given cumulative probability accounting for gaps.
   *
   * @param list The list to look for name in
   * @param probability the cumulative probability to search for (between 0 and 1)
   * @return the name object
   */
  private Name getNameObject(List<? extends Name> list, double probability) {
    int index =
        Collections.binarySearch(
            list, new Name(null, probability), locateNameByCumulativeProbability);
    if (index >= 0) {
      return list.get(index);
    } else if (-index > list.size()) {
      throw new RuntimeException(
          "Invalid probability provided - ("
              + probability
              + "). Max allowed for this list is "
              + getMax(list));
    } else {
      return list.get((-index) - 1);
    }
  }

  /**
   * Gets a random person.
   *
   * @return a random person
   */
  public Person getPerson() {
    return getPerson(random.nextLong());
  }

  private double getDoubleFromLong(long number, long divisor) {
    double remainder = Math.abs((number * 1.0d) / (divisor * 1.0d));
    return remainder - Math.floor(remainder);
  }

  /**
   * Gets a person for a given number.
   *
   * @param number the number
   * @return the person
   */
  public Person getPerson(long number) {
    double firstNameProbability = getDoubleFromLong(number, 66767676967L);
    var p = Person.builder();
    if (number > 0) {
      p.gender("M");
      p.firstName(getMaleName(firstNameProbability));
    } else {
      p.gender("F");
      p.firstName(getFemaleName(firstNameProbability));
    }
    double lastNameProbability = getDoubleFromLong(number, 41935324L);
    LastName nameObject =
        (LastName)
            getNameObject(
                dataUtil.getLastNames(), lastNameProbability * getMax(dataUtil.getLastNames()));

    p.lastName(nameObject.getValue());
    double raceProbability = getDoubleFromLong(number, 21321567657L);
    p.race(nameObject.getRace(raceProbability));
    return p.build();
  }
}
