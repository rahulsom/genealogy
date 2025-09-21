package com.github.rahulsom.genealogy;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represents a Lastname
 */
@Getter
@ToString(callSuper = true)
public class LastName extends Name {
    private static final double MAX_PROBABILITY = 1.0d;
    /**
     * The percentage of people with this last name who are white.
     * @return The percentage of people with this last name who are white.
     */
    private Double percentWhite;
    /**
     * The percentage of people with this last name who are black.
     * @return The percentage of people with this last name who are black.
     */
    private Double percentBlack;
    /**
     * The percentage of people with this last name who are Asian or Pacific Islander.
     * @return The percentage of people with this last name who are Asian or Pacific Islander.
     */
    private Double percentAsianOrPacificIslander;
    /**
     * The percentage of people with this last name who are Alaskan or Native American.
     * @return The percentage of people with this last name who are Alaskan or Native American.
     */
    private Double percentAlaskanOrNativeAmerican;
    /**
     * The percentage of people with this last name who are of mixed race.
     * @return The percentage of people with this last name who are of mixed race.
     */
    private Double percentMixedRace;
    /**
     * The percentage of people with this last name who are Hispanic.
     * @return The percentage of people with this last name who are Hispanic.
     */
    private Double percentHispanic;

    /**
     * Creates a LastName.
     *
     * @param value                        The name
     * @param cumulativeProbability        The cumulative probability of the name
     * @param percentWhite                 The percentage of people with this last name who are white
     * @param percentBlack                 The percentage of people with this last name who are black
     * @param percentAsianOrPacificIslander The percentage of people with this last name who are Asian or Pacific Islander
     * @param percentAlaskanOrNativeAmerican The percentage of people with this last name who are Alaskan or Native American
     * @param percentMixedRace             The percentage of people with this last name who are of mixed race
     * @param percentHispanic              The percentage of people with this last name who are Hispanic
     */
    public LastName(
            String value, Double cumulativeProbability,
            Double percentWhite, Double percentBlack, Double percentAsianOrPacificIslander,
            Double percentAlaskanOrNativeAmerican, Double percentMixedRace, Double percentHispanic) {
        super(value, cumulativeProbability);
        this.percentWhite = percentWhite;
        this.percentBlack = percentBlack;
        this.percentAsianOrPacificIslander = percentAsianOrPacificIslander;
        this.percentAlaskanOrNativeAmerican = percentAlaskanOrNativeAmerican;
        this.percentMixedRace = percentMixedRace;
        this.percentHispanic = percentHispanic;
        double accountedRace = 0;
        int unaccountedRaces = 0;
        if (percentWhite == null) {
            unaccountedRaces ++;
        } else {
            accountedRace += percentWhite;
        }
        if (percentBlack == null) {
            unaccountedRaces ++;
        } else {
            accountedRace += percentBlack;
        }
        if (percentAsianOrPacificIslander == null) {
            unaccountedRaces ++;
        } else {
            accountedRace += percentAsianOrPacificIslander;
        }
        if (percentAlaskanOrNativeAmerican == null) {
            unaccountedRaces ++;
        } else {
            accountedRace += percentAlaskanOrNativeAmerican;
        }
        if (percentMixedRace == null) {
            unaccountedRaces ++;
        } else {
            accountedRace += percentMixedRace;
        }
        if (percentHispanic == null) {
            unaccountedRaces ++;
        } else {
            accountedRace += percentHispanic;
        }

        if (this.percentWhite == null) {
            this.percentWhite = (MAX_PROBABILITY - accountedRace)/unaccountedRaces;
        }
        if (this.percentBlack == null) {
            this.percentBlack = (MAX_PROBABILITY - accountedRace)/unaccountedRaces;
        }
        if (this.percentAsianOrPacificIslander == null) {
            this.percentAsianOrPacificIslander = (MAX_PROBABILITY - accountedRace)/unaccountedRaces;
        }
        if (this.percentAlaskanOrNativeAmerican == null) {
            this.percentAlaskanOrNativeAmerican = (MAX_PROBABILITY - accountedRace)/unaccountedRaces;
        }
        if (this.percentMixedRace == null) {
            this.percentMixedRace = (MAX_PROBABILITY - accountedRace)/unaccountedRaces;
        }
        if (this.percentHispanic == null) {
            this.percentHispanic = (MAX_PROBABILITY - accountedRace)/unaccountedRaces;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LastName lastName = (LastName) o;
        return Objects.equals(percentWhite, lastName.percentWhite) &&
                Objects.equals(percentBlack, lastName.percentBlack) &&
                Objects.equals(percentAsianOrPacificIslander, lastName.percentAsianOrPacificIslander) &&
                Objects.equals(percentAlaskanOrNativeAmerican, lastName.percentAlaskanOrNativeAmerican) &&
                Objects.equals(percentMixedRace, lastName.percentMixedRace) &&
                Objects.equals(percentHispanic, lastName.percentHispanic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), percentWhite, percentBlack, percentAsianOrPacificIslander, percentAlaskanOrNativeAmerican, percentMixedRace, percentHispanic);
    }
}
