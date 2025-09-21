package com.github.rahulsom.genealogy;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton that stores data so multiple instances of NameDbUsa can be created.
 */
@Getter
public class DataUtil {

    static class Holder {
        private static final DataUtil instance = new DataUtil();
    }

    static DataUtil getInstance() {
        return Holder.instance;
    }

    private final List<Name> maleNames = new ArrayList<>();
    private final List<Name> femaleNames = new ArrayList<>();
    private final List<LastName> lastNames = new ArrayList<>();

  private DataUtil() {
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

    private static Double getDouble(String string, double divisor) {
        return string.matches("[0-9.]+") ? Double.parseDouble(string) / divisor : null;
    }

    private static Double getDouble(String string) {
        return getDouble(string, 1.0d);
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
        if (lastNameStream == null) {
            throw new RuntimeException("Could not find resource " + resourceName);
        }
        BufferedReader lastNameReader = new BufferedReader(new InputStreamReader(lastNameStream));
        try {
            int index = 0;
            while (lastNameReader.ready()) {
                String line = lastNameReader.readLine();
                processor.processLine(line, index++);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load resource " + resourceName, e);
        }
    }
}
