package com.github.rahulsom.genealogy;

/**
 * Represents a Lastname
 */
public class LastName extends Name {
    private double percentWhite, percentBlack, percentAsianOrPacificIslander, percentAlaskanOrNativeAmerican,
            percentMixedRace, percentHispanic;

    public LastName(
            String value, double cumulativeProbability,
            double percentWhite, double percentBlack, double percentAsianOrPacificIslander,
            double percentAlaskanOrNativeAmerican, double percentMixedRace, double percentHispanic) {
        super(value, cumulativeProbability);
        this.percentWhite = percentWhite;
        this.percentBlack = percentBlack;
        this.percentAsianOrPacificIslander = percentAsianOrPacificIslander;
        this.percentAlaskanOrNativeAmerican = percentAlaskanOrNativeAmerican;
        this.percentMixedRace = percentMixedRace;
        this.percentHispanic = percentHispanic;
    }

    public double getPercentWhite() {
        return percentWhite;
    }

    public double getPercentBlack() {
        return percentBlack;
    }

    public double getPercentAsianOrPacificIslander() {
        return percentAsianOrPacificIslander;
    }

    public double getPercentAlaskanOrNativeAmerican() {
        return percentAlaskanOrNativeAmerican;
    }

    public double getPercentMixedRace() {
        return percentMixedRace;
    }

    public double getPercentHispanic() {
        return percentHispanic;
    }
}
