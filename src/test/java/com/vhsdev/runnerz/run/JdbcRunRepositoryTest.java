package com.vhsdev.runnerz.run;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
class JdbcRunRepositoryTest {

  @Autowired
  JdbcRunRepository repository;

  @BeforeEach
  void setUp() {
    repository.create(
        new Run(1, "Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plus(30,
            ChronoUnit.MINUTES), 3, Location.INDOOR, null));
    repository.create(new Run(2, "Wednesday Evening Run", LocalDateTime.now(),
        LocalDateTime.now().plus(60, ChronoUnit.MINUTES), 6, Location.INDOOR, null));
  }

  @Test
  void shouldFindAllRuns() {
    List<Run> runs = repository.findAll();
    assertEquals(2, runs.size());
  }

  @Test
  void shouldFindRunWithValidId() {
    Run run = repository.findById(1).get();
    assertEquals("Monday Morning Run", run.title());
    assertEquals(3, run.miles());
  }

  @Test
  void shouldNotFindRunWithInvalidId() {
    assertFalse(repository.findById(3).isPresent());
  }

  @Test
  void shouldUpdateRun() {
    Run run = new Run(1, "Monday Morning Run", LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 5, Location.OUTDOOR, null);
    repository.update(run, 1);
    Run updatedRun = repository.findById(1).get();
    assertEquals("Monday Morning Run", updatedRun.title());
    assertEquals(5, updatedRun.miles());
    assertEquals(Location.OUTDOOR, updatedRun.location());
  }

  @Test
  void shouldDeleteRun() {
    repository.delete(1);
    List<Run> runs = repository.findAll();
    assertEquals(1, runs.size());
  }
}
