package com.github.rahulsom.genealogy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    
    private static Double getDouble(String string) {
        return string.matches("[0-9\\.]+") ? Double.valueOf(string) : null;
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
                            getDouble(parts[5]),
                            getDouble(parts[6]),
                            getDouble(parts[7]),
                            getDouble(parts[8]),
                            getDouble(parts[9]),
                            getDouble(parts[10])
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

    private final SecureRandom random = new SecureRandom();

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

    public double getMaleNameMax() {
        return getMax(maleNames);
    }

    public double getFemaleNameMax() {
        return getMax(femaleNames);
    }

    public double getLastNameMax() {
        return getMax(lastNames);
    }

    public String getMaleName(double probability) {
        return getName(maleNames, probability);
    }

    public String getMaleName() {
        double probability = random.nextDouble() * getMaleNameMax();
        return getMaleName(probability);
    }

    public String getFemaleName(double probability) {
        return getName(femaleNames, probability);
    }

    public String getFemaleName() {
        double probability = random.nextDouble() * getFemaleNameMax();
        return getFemaleName(probability);
    }

    public String getLastName(double probability) {
        return getName(lastNames, probability);
    }

    public String getLastName() {
        double probability = random.nextDouble() * getLastNameMax();
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
     * @param probability the cumulative probability to search for
     * @return the name
     */
    private String getName(List<? extends Name> list, double probability) {
        Name name = getNameObject(list, probability);
        return name.getValue();
    }

    /**
     * Finds name at a given cumulative probability accounting for gaps.
     *
     * @param list        The list to look for name in
     * @param probability the cumulative probability to search for
     * @return the name object
     */
    private Name getNameObject(List<? extends Name> list, double probability) {
        int index = Collections.binarySearch(list, new Name(null, probability), locateNameByCumulativeProbability);
        if (index >= 0) {
            return list.get(index);
        } else if (-index > list.size()) {
            throw new RuntimeException("Invalid probability provided. Max allowed for this list is " + getMax(list));
        } else {
            return list.get((-index) - 1);
        }
    }

    public Person getPerson() {
        Person p = new Person();
        if (random.nextBoolean()) {
          p.gender = "M";
          p.firstName = getMaleName();
        } else {
          p.gender = "F";
          p.firstName = getFemaleName();
        }
        double probability = random.nextDouble() * getLastNameMax();
        LastName nameObject = (LastName) getNameObject(lastNames, probability);

        p.lastName = nameObject.getValue();
        double raceProbability = random.nextDouble() * 100.0d;
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
        }, "Time for 1000000 males", 3);
        profile(new Task() {
            @Override
            void run() {
                for (int i = 0; i < 1000000; i++) {
                    instance[0].getFemaleName();
                }
            }
        }, "Time for 1000000 females", 3);
        profile(new Task() {
            @Override
            void run() {
                for (int i = 0; i < 1000000; i++) {
                    instance[0].getLastName();
                }
            }
        }, "Time for 1000000 last names", 3);
        profile(new Task() {
            @Override
            void run() {
                for (int i = 0; i < 1000000; i++) {
                    instance[0].getPerson();
                }
            }
        }, "Time for 1000000 persons", 3);
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
    static void profile(Task task, String message, int tries) {
        for (int i = 0; i < tries; i++) {
            long start = System.nanoTime();
            task.run();
            long finish = System.nanoTime();
            System.out.println(
                    MessageFormat.format("[Try {2}] {1}: {0}ms", (finish - start) / 1000000.0, message, i + 1)
            );
        }

    }
}
