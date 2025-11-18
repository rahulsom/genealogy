package com.github.rahulsom.genealogy;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.*;

/** Represents a Lastname */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LastName extends Name {
  private static final double MAX_PROBABILITY = 1.0d;

  /** The percentage of people with this last name who are white. */
  private Double percentWhite;

  /** The percentage of people with this last name who are black. */
  private Double percentBlack;

  /** The percentage of people with this last name who are Asian or Pacific Islander. */
  private Double percentAsianOrPacificIslander;

  /** The percentage of people with this last name who are Alaskan or Native American. */
  private Double percentAlaskanOrNativeAmerican;

  /** The percentage of people with this last name who are of mixed race. */
  private Double percentMixedRace;

  /** The percentage of people with this last name who are Hispanic. */
  private Double percentHispanic;

  /**
   * Creates a LastName.
   *
   * @param value The name
   * @param cumulativeProbability The cumulative probability of the name
   * @param percentWhite The percentage of people with this last name who are white
   * @param percentBlack The percentage of people with this last name who are black
   * @param percentAsianOrPacificIslander The percentage of people with this last name who are Asian
   *     or Pacific Islander
   * @param percentAlaskanOrNativeAmerican The percentage of people with this last name who are
   *     Alaskan or Native American
   * @param percentMixedRace The percentage of people with this last name who are of mixed race
   * @param percentHispanic The percentage of people with this last name who are Hispanic
   */
  public LastName(
      String value,
      Double cumulativeProbability,
      Double percentWhite,
      Double percentBlack,
      Double percentAsianOrPacificIslander,
      Double percentAlaskanOrNativeAmerican,
      Double percentMixedRace,
      Double percentHispanic) {
    super(value, cumulativeProbability);
    this.percentWhite = percentWhite;
    this.percentBlack = percentBlack;
    this.percentAsianOrPacificIslander = percentAsianOrPacificIslander;
    this.percentAlaskanOrNativeAmerican = percentAlaskanOrNativeAmerican;
    this.percentMixedRace = percentMixedRace;
    this.percentHispanic = percentHispanic;
    var accountedRace = new AtomicReference<>(0.0d);
    var unaccountedRaces = new AtomicReference<>(0);
    accountForRace(percentWhite, unaccountedRaces, accountedRace);
    accountForRace(percentBlack, unaccountedRaces, accountedRace);
    accountForRace(percentAsianOrPacificIslander, unaccountedRaces, accountedRace);
    accountForRace(percentAlaskanOrNativeAmerican, unaccountedRaces, accountedRace);
    accountForRace(percentMixedRace, unaccountedRaces, accountedRace);
    accountForRace(percentHispanic, unaccountedRaces, accountedRace);

    this.percentWhite = updateIfNull(accountedRace, unaccountedRaces, this.percentWhite);
    this.percentBlack = updateIfNull(accountedRace, unaccountedRaces, this.percentBlack);
    this.percentAsianOrPacificIslander =
        updateIfNull(accountedRace, unaccountedRaces, this.percentAsianOrPacificIslander);
    this.percentAlaskanOrNativeAmerican =
        updateIfNull(accountedRace, unaccountedRaces, this.percentAlaskanOrNativeAmerican);
    this.percentMixedRace = updateIfNull(accountedRace, unaccountedRaces, this.percentMixedRace);
    this.percentHispanic = updateIfNull(accountedRace, unaccountedRaces, this.percentHispanic);
  }

  private Double updateIfNull(
      AtomicReference<Double> accountedRace,
      AtomicReference<Integer> unaccountedRaces,
      Double percentForRace) {
    return Optional.ofNullable(percentForRace)
        .orElseGet(() -> (MAX_PROBABILITY - accountedRace.get()) / unaccountedRaces.get());
  }

  private static void accountForRace(
      Double percentForRace,
      AtomicReference<Integer> unaccountedRaces,
      AtomicReference<Double> accountedRace) {
    if (percentForRace == null) {
      unaccountedRaces.set(unaccountedRaces.get() + 1);
    } else {
      accountedRace.set(accountedRace.get() + percentForRace);
    }
  }

  /**
   * Gets a race based on a probability.
   *
   * @param raceProbability The probability of the race
   * @return The race
   */
  public Race getRace(double raceProbability) {
    Object[][] data = {
      {Race.White, percentWhite},
      {Race.Black, percentBlack},
      {Race.AsianOrPacificIslander, percentAsianOrPacificIslander},
      {Race.AlaskanOrNativeAmerican, percentAlaskanOrNativeAmerican},
      {Race.MixedRace, percentMixedRace},
      {Race.Hispanic, percentHispanic}
    };

    Double cumulative = 0.0d;
    for (Object[] entry : data) {
      cumulative += (Double) entry[1];
      if (cumulative > raceProbability) {
        return (Race) entry[0];
      }
    }
    return (Race) data[0][0];
  }
}
