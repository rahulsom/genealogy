package com.github.rahulsom.genealogy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NameDbUsaTest {
  private final NameDbUsa nameDbUsa = NameDbUsa.getInstance();

  @Test
  void getMaleNameRunsFast() {
    long start = System.nanoTime();
    for (int i = 0; i < 1000000; i++) {
      nameDbUsa.getMaleName();
    }
    long end = System.nanoTime();
    assertThat((end - start) / 1_000_000).isLessThan(150);
  }

  @Test
  void getFemaleNameRunsFast() {
    long start = System.nanoTime();
    for (int i = 0; i < 1000000; i++) {
      nameDbUsa.getFemaleName();
    }
    long end = System.nanoTime();
    assertThat((end - start) / 1_000_000).isLessThan(150);
  }

  @Test
  void getLastNameRunsFast() {
    long start = System.nanoTime();
    for (int i = 0; i < 1000000; i++) {
      nameDbUsa.getLastName();
    }
    long end = System.nanoTime();
    assertThat((end - start) / 1_000_000).isLessThan(300);
  }

  @Test
  void getPersonRunsFast() {
    long start = System.nanoTime();
    for (int i = 0; i < 1000000; i++) {
      nameDbUsa.getPerson();
    }
    long end = System.nanoTime();
    assertThat((end - start) / 1_000_000).isLessThan(600);
  }
}
