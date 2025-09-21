package com.github.rahulsom.genealogy;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Util to generate names based on US Census data
 */
public class NameDbUsa {

    private final DataUtil dataUtil;

    private final Random random;
    private static final SecureRandom secureRandom = new SecureRandom();

    public NameDbUsa(Random random) {
        this.random = random;
        dataUtil = DataUtil.getInstance();
    }

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
        return list.get(list.size() - 1).getCumulativeProbability();
    }

    public String getMaleName(double probability) {
        return getName(dataUtil.getMaleNames(), probability);
    }

    public String getMaleName() {
        double probability = random.nextDouble();
        return getMaleName(probability);
    }

    public String getFemaleName(double probability) {
        return getName(dataUtil.getFemaleNames(), probability);
    }

    public String getFemaleName() {
        double probability = random.nextDouble();
        return getFemaleName(probability);
    }

    public String getLastName(double probability) {
        return getName(dataUtil.getLastNames(), probability);
    }

    public String getLastName() {
        double probability = random.nextDouble();
        return getLastName(probability);
    }

    /**
     * Comparator to find the name at a given cumulative probability
     */
    private final Comparator<Name> locateNameByCumulativeProbability = Comparator.comparingDouble(Name::getCumulativeProbability);

    /**
     * Finds name at a given cumulative probability accounting for gaps.
     *
     * @param list        The list to look for name in
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
     * @param list        The list to look for name in
     * @param probability the cumulative probability to search for (between 0 and 1)
     * @return the name object
     */
    private Name getNameObject(List<? extends Name> list, double probability) {
        int index = Collections.binarySearch(list, new Name(null, probability), locateNameByCumulativeProbability);
        if (index >= 0) {
            return list.get(index);
        } else if (-index > list.size()) {
            throw new RuntimeException("Invalid probability provided - (" + probability +
                    "). Max allowed for this list is " + getMax(list));
        } else {
            return list.get((-index) - 1);
        }
    }

    public Person getPerson() {
        return getPerson(random.nextLong());
    }

    private double getDoubleFromLong(long number, long divisor) {
        double remainder = Math.abs((number * 1.0d) / (divisor * 1.0d));
		double retval = remainder - Math.floor(remainder);
		return retval;
    }

    public Person getPerson(long number) {
		double firstNameProbability = getDoubleFromLong(number, 66767676967L);
        Person p = new Person();
        if (number > 0) {
            p.gender = "M";
            p.firstName = getMaleName(firstNameProbability);
        } else {
            p.gender = "F";
            p.firstName = getFemaleName(firstNameProbability);
        }
		double lastNameProbability = getDoubleFromLong(number, 41935324L);
        LastName nameObject = (LastName) getNameObject(dataUtil.getLastNames(),
				lastNameProbability * getMax(dataUtil.getLastNames()));

        p.lastName = nameObject.getValue();
		double raceProbability = getDoubleFromLong(number, 21321567657L);
        p.race = nameObject.getRace(raceProbability);
        return p;
    }

    public static void main(String[] args) {
        final NameDbUsa[] instance = new NameDbUsa[1];
        profile(new Task() {
            @Override
            void run() {
                instance[0] = NameDbUsa.getInstance();
            }
        }, "Time for init", 1);

        profile(new Task() {
            @Override
            void run() {
                for (int i = 0; i < 1000000; i++) {
                    instance[0].getMaleName();
                }
            }
        }, "Time for 1000000 males", 5);
        profile(new Task() {
            @Override
            void run() {
                for (int i = 0; i < 1000000; i++) {
                    instance[0].getFemaleName();
                }
            }
        }, "Time for 1000000 females", 5);
        profile(new Task() {
            @Override
            void run() {
                for (int i = 0; i < 1000000; i++) {
                    instance[0].getLastName();
                }
            }
        }, "Time for 1000000 last names", 5);
        profile(new Task() {
            @Override
            void run() {
                for (int i = 0; i < 1000000; i++) {
                    instance[0].getPerson();
                }
            }
        }, "Time for 1000000 persons", 5);
    }

    /**
     * Task that needs to be profiled
     */
    private static abstract class Task {
        abstract void run();
    }

    /**
     * Poor mans profiler
     *
     * @param task    task to execute
     * @param message message identifying the task
     * @param tries   number of times task needs to be executed
     */
    private static void profile(Task task, String message, int tries) {
        for (int i = 0; i < tries; i++) {
            long start = System.nanoTime();
            task.run();
            long finish = System.nanoTime();
            System.out.printf("[Try %d] %-30s: %-5.2fms%n", i + 1, message, (finish - start) / 1000000.0);
        }

    }
}
