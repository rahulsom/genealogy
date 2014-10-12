package com.github.rahulsom.genealogy;

/**
 * Created by rahulsomasunderam on 5/31/14.
 */
public class Person {
    String firstName;
    String lastName;
    Race race;
    String gender;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Race getRace() {
        return race;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", race='" + race + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
