package com.vhsdev.runnerz.run;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryRunRepositoryTest {

  InMemoryRunRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryRunRepository();
    repository.create(
        new Run(1, "Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plus(30,
            ChronoUnit.MINUTES), 3,
            Location.INDOOR));
    repository.create(new Run(2, "Wednesday Evening Run", LocalDateTime.now(),
        LocalDateTime.now().plus(60, ChronoUnit.MINUTES), 6,
        Location.INDOOR));
  }

  @Test
  void shouldFindAllRuns() {
    List<Run> runs = repository.findAll();
    assertEquals(2, runs.size(), "Should find 2 runs");
  }

  @Test
  void shouldFindRunWithValidId() {
    Run run = repository.findById(1).get();
    assertEquals("Monday Morning Run", run.title());
    assertEquals(3, run.miles());
  }

  @Test
  void shouldNotFindRunWithInvalidId() {
    assertThrows(RunNotFoundException.class, () -> repository.findById(3));
  }

  @Test
  void shouldCreateNewRun() {
    repository.create(new Run(3, "Friday Morning Run", LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 3,
        Location.OUTDOOR));
    List<Run> runs = repository.findAll();
    assertEquals(3, runs.size(), "Should find 3 runs");
  }

  @Test
  void shouldUpdateRun() {
    Run run = new Run(1, "Monday Morning Run", LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 5,
        Location.OUTDOOR);
    repository.update(run, 1);
    Run updatedRun = repository.findById(1).get();
    assertEquals("Monday Morning Run", updatedRun.title(), "Should be Monday Morning Run");
    assertEquals(5, updatedRun.miles(), "Should be 5 miles");
    assertEquals(Location.OUTDOOR, updatedRun.location(), "Should be outdoor");
  }

  @Test
  void shouldDeleteRun() {
    repository.delete(1);
    List<Run> runs = repository.findAll();
    assertEquals(1, runs.size(), "Should find 1 run");
  }
}
