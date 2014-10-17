package com.github.rahulsom.genealogy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.*;

/**
 * Util to generate names based on US Census data
 */
public class NameDbUsa {

    private static class Holder {
        public static final NameDbUsa instance = new NameDbUsa();
    }

    public static NameDbUsa getInstance() {
        return Holder.instance;
    }

    private static Double getDouble(String string, double divisor) {
        return string.matches("[0-9\\.]+") ? Double.valueOf(string) / divisor : null;
    }

    private static Double getDouble(String string) {
        return getDouble(string, 1.0d);
    }

    private NameDbUsa() {
        processResource("last.csv", new AbstractProcessor() {
            @Override
            public void processLine(String line, long index) {
                if (index > 0) {
                    String[] parts = line.split(",");
                    lastNames.add(new LastName(
                            parts[0],
                            getDouble(parts[4]),
                            getDouble(parts[5], 100.0),
                            getDouble(parts[6], 100.0),
                            getDouble(parts[7], 100.0),
                            getDouble(parts[8], 100.0),
                            getDouble(parts[9], 100.0),
                            getDouble(parts[10], 100.0)
                    ));
                }
            }
        });

        processResource("dist.female.first", new AbstractProcessor() {
            @Override
            public void processLine(String line, long index) {
                String[] parts = line.split(" +");
                femaleNames.add(new Name(parts[0], Double.parseDouble(parts[2])));
            }
        });

        processResource("dist.male.first", new AbstractProcessor() {
            @Override
            public void processLine(String line, long index) {
                String[] parts = line.split(" +");
                maleNames.add(new Name(parts[0], Double.parseDouble(parts[2])));
            }
        });
    }

    private List<Name> maleNames = new ArrayList<Name>();
    private List<Name> femaleNames = new ArrayList<Name>();
    private List<LastName> lastNames = new ArrayList<LastName>();

    private final SecureRandom sRandom = new SecureRandom();
    private final Random random = new Random(sRandom.nextLong());

    public void reset() {
        random.setSeed(sRandom.nextLong());
    }

    /**
     * Think of eachLineWithIndex()
     */
    private static abstract class AbstractProcessor {
        public abstract void processLine(String line, long index);
    }

    /**
     * Processes a given resource using provided closure
     *
     * @param resourceName resource to fetch from classpath
     * @param processor    how to process the resource
     */
    private static void processResource(String resourceName, AbstractProcessor processor) {
        InputStream lastNameStream = NameDbUsa.class.getClassLoader().getResourceAsStream(resourceName);
        BufferedReader lastNameReader = new BufferedReader(new InputStreamReader(lastNameStream));
        try {
            int index = 0;
            while (lastNameReader.ready()) {
                String line = lastNameReader.readLine();
                processor.processLine(line, index++);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return getName(maleNames, probability);
    }

    public String getMaleName() {
        double probability = random.nextDouble();
        return getMaleName(probability);
    }

    public String getFemaleName(double probability) {
        return getName(femaleNames, probability);
    }

    public String getFemaleName() {
        double probability = random.nextDouble();
        return getFemaleName(probability);
    }

    public String getLastName(double probability) {
        return getName(lastNames, probability);
    }

    public String getLastName() {
        double probability = random.nextDouble();
        return getLastName(probability);
    }

    /**
     * Comparator to find the name at a given cumulative probability
     */
    private final Comparator<Name> locateNameByCumulativeProbability = new Comparator<Name>() {
        @Override
        public int compare(Name o1, Name o2) {
            return Double.compare(o1.getCumulativeProbability(), o2.getCumulativeProbability());
        }
    };

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
        double retval = Math.abs((number * 1.0d) / (divisor * 1.0d));
        return retval - Math.floor(retval);
    }

    public Person getPerson(long number) {
        double firstNameProbability = getDoubleFromLong(number, 66767676967l);
        double lastNameProbability = getDoubleFromLong(number, 41935324l);
        Person p = new Person();
        if (number > 0) {
            p.gender = "M";
            p.firstName = getMaleName(firstNameProbability);
        } else {
            p.gender = "F";
            p.firstName = getFemaleName(firstNameProbability);
        }
        LastName nameObject = (LastName) getNameObject(lastNames, lastNameProbability);

        p.lastName = nameObject.getValue();
        double raceProbability = getDoubleFromLong(number, 21321567657l);
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
            System.out.println(
                    String.format("[Try %d] %-30s: %-5.2fms", i+1, message, (finish - start) / 1000000.0)
            );
        }

    }
}
