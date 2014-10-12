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

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Race getRace() {
    return race;
  }

  public void setRace(Race race) {
    this.race = race;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
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
