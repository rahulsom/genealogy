package com.github.rahulsom.genealogy;

/**
 * Represents a Lastname
 */
public class LastName extends Name {
    private static final double MAX_PROBABILITY = 1.0d;
    private Double percentWhite, percentBlack, percentAsianOrPacificIslander, percentAlaskanOrNativeAmerican,
            percentMixedRace, percentHispanic;

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

    public Double getPercentWhite() {
        return percentWhite;
    }

    public Double getPercentBlack() {
        return percentBlack;
    }

    public Double getPercentAsianOrPacificIslander() {
        return percentAsianOrPacificIslander;
    }

    public Double getPercentAlaskanOrNativeAmerican() {
        return percentAlaskanOrNativeAmerican;
    }

    public Double getPercentMixedRace() {
        return percentMixedRace;
    }

    public Double getPercentHispanic() {
        return percentHispanic;
    }

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
