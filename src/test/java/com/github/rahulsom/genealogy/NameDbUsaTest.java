package com.github.rahulsom.genealogy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NameDbUsaTest {
  @Test
  void getMaleNameRunsFast() {
    var nameDbUsa = NameDbUsa.getInstance();
    long start = System.nanoTime();
    for (int i = 0; i < 1_000_000; i++) {
      nameDbUsa.getMaleName();
    }
    long end = System.nanoTime();
    assertThat((end - start) / 1_000_000).isLessThan(400);
  }

  @Test
  void getFemaleNameRunsFast() {
    var nameDbUsa = NameDbUsa.getInstance();
    long start = System.nanoTime();
    for (int i = 0; i < 1_000_000; i++) {
      nameDbUsa.getFemaleName();
    }
    long end = System.nanoTime();
    assertThat((end - start) / 1_000_000).isLessThan(400);
  }

  @Test
  void getLastNameRunsFast() {
    var nameDbUsa = NameDbUsa.getInstance();
    long start = System.nanoTime();
    for (int i = 0; i < 1_000_000; i++) {
      nameDbUsa.getLastName();
    }
    long end = System.nanoTime();
    assertThat((end - start) / 1_000_000).isLessThan(800);
  }

  @Test
  void getPersonRunsFast() {
    var nameDbUsa = NameDbUsa.getInstance();
    long start = System.nanoTime();
    for (int i = 0; i < 1_000_000; i++) {
      nameDbUsa.getPerson();
    }
    long end = System.nanoTime();
    assertThat((end - start) / 1_000_000).isLessThan(1600);
  }

  @Mock Random random;

  @InjectMocks NameDbUsa nameDbUsa;

  @Test
  void getMaleName() {
    when(random.nextDouble()).thenReturn(0.5);
    assertThat(nameDbUsa.getMaleName()).isEqualTo("HAROLD");
  }

  @Test
  void getFemaleName() {
    when(random.nextDouble()).thenReturn(0.5);
    assertThat(nameDbUsa.getFemaleName()).isEqualTo("TIFFANY");
  }

  @Test
  void getLastName() {
    when(random.nextDouble()).thenReturn(0.5);
    assertThat(nameDbUsa.getLastName()).isEqualTo("JEFFRIES");
  }

  @Test
  void getPerson() {
    when(random.nextLong()).thenReturn(12345L);
    Person person = nameDbUsa.getPerson();
    assertThat(person.firstName()).isEqualTo("JAMES");
    assertThat(person.lastName()).isEqualTo("SMITH");
    assertThat(person.race()).isEqualTo(Race.White);
  }

  @Test
  void getMaleNameWithProbability() {
    assertThat(nameDbUsa.getMaleName(0.6)).isEqualTo("LOUIS");
  }

  @Test
  void getFemaleNameWithProbability() {
    assertThat(nameDbUsa.getFemaleName(0.7)).isEqualTo("MATTIE");
  }

  @Test
  void getLastNameWithProbability() {
    assertThat(nameDbUsa.getLastName(0.8)).isEqualTo("MASER");
  }

  @Test
  void getPersonWithPositiveNumber() {
    Person person = nameDbUsa.getPerson(12_345L);
    assertThat(person.firstName()).isEqualTo("JAMES");
    assertThat(person.lastName()).isEqualTo("SMITH");
    assertThat(person.gender()).isEqualTo("M");
    assertThat(person.race()).isEqualTo(Race.White);
  }

  @Test
  void getPersonWithNegativeNumber() {
    Person person = nameDbUsa.getPerson(-12_345L);
    assertThat(person.firstName()).isEqualTo("MARY");
    assertThat(person.lastName()).isEqualTo("SMITH");
    assertThat(person.race()).isEqualTo(Race.White);
  }
}
