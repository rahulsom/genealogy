package com.github.rahulsom.genealogy;

import java.util.Objects;

/**
 * Created by rahulsomasunderam on 5/31/14.
 */
public class Person {
    /**
     * The person's first name.
     */
    String firstName;
    /**
     * The person's last name.
     */
    String lastName;
    /**
     * The person's race.
     */
    Race race;
    /**
     * The person's gender.
     */
    String gender;

    /**
     * Default constructor.
     */
    public Person() {
    }

    /**
     * Creates a person.
     *
     * @param firstName The first name
     * @param lastName  The last name
     * @param race      The race
     * @param gender    The gender
     */
    public Person(String firstName, String lastName, Race race, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.race = race;
        this.gender = gender;
    }

    /**
     * Gets the first name.
     *
     * @return The first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName The first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return The last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName The last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the race.
     *
     * @return The race
     */
    public Race getRace() {
        return race;
    }

    /**
     * Sets the race.
     *
     * @param race The race
     */
    public void setRace(Race race) {
        this.race = race;
    }

    /**
     * Gets the gender.
     *
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     *
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                race == person.race &&
                Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, race, gender);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", race=" + race +
                ", gender='" + gender + '\'' +
                '}';
    }

    /**
     * Creates a new builder.
     * @return a new builder
     */
    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    /**
     * Builder for Person.
     */
    public static class PersonBuilder {
        private String firstName;
        private String lastName;
        private Race race;
        private String gender;

        /**
         * Default constructor for the builder.
         */
        PersonBuilder() {
        }

        /**
         * Sets the first name.
         * @param firstName the first name
         * @return the builder
         */
        public PersonBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Sets the last name.
         * @param lastName the last name
         * @return the builder
         */
        public PersonBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Sets the race.
         * @param race the race
         * @return the builder
         */
        public PersonBuilder race(Race race) {
            this.race = race;
            return this;
        }

        /**
         * Sets the gender.
         * @param gender the gender
         * @return the builder
         */
        public PersonBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        /**
         * Builds the person.
         * @return the person
         */
        public Person build() {
            return new Person(firstName, lastName, race, gender);
        }
    }
}
