package com.github.rahulsom.genealogy;

import java.util.*;

/**
 * Represents a Lastname
 */
public class LastName extends Name {
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
            this.percentWhite = (100.0d - accountedRace)/unaccountedRaces;
        }
        if (this.percentBlack == null) {
            this.percentBlack = (100.0d - accountedRace)/unaccountedRaces;
        }
        if (this.percentAsianOrPacificIslander == null) {
            this.percentAsianOrPacificIslander = (100.0d - accountedRace)/unaccountedRaces;
        }
        if (this.percentAlaskanOrNativeAmerican == null) {
            this.percentAlaskanOrNativeAmerican = (100.0d - accountedRace)/unaccountedRaces;
        }
        if (this.percentMixedRace == null) {
            this.percentMixedRace = (100.0d - accountedRace)/unaccountedRaces;
        }
        if (this.percentHispanic == null) {
            this.percentHispanic = (100.0d - accountedRace)/unaccountedRaces;
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
        LinkedHashMap<Race, Double> map = new LinkedHashMap<>(6);
        map.put(Race.White, percentWhite);
        map.put(Race.Black, percentBlack);
        map.put(Race.AsianOrPacificIslander, percentAsianOrPacificIslander);
        map.put(Race.AlaskanOrNativeAmerican, percentAlaskanOrNativeAmerican);
        map.put(Race.MixedRace, percentMixedRace);
        map.put(Race.Hispanic, percentHispanic);
        List<Map.Entry<Race, Double>> entries =
                new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Race, Double>>() {
            public int compare(Map.Entry<Race, Double> a, Map.Entry<Race, Double> b){
                return b.getValue().compareTo(a.getValue());
            }
        });
        Map<Race, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Race, Double> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        Double cumulative = 0.0d;

        for (Map.Entry<Race, Double> entry : sortedMap.entrySet()) {
            cumulative += entry.getValue();
            if (cumulative > raceProbability) {
                return entry.getKey();
            }
        }
        Object[] objects = sortedMap.entrySet().toArray();
        return ((Map.Entry<Race, Double>)objects[0]).getKey();
    }

}
