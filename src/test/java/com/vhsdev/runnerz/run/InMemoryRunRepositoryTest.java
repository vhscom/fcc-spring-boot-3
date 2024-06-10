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
            Location.INDOOR, null));
    repository.create(new Run(2, "Wednesday Evening Run", LocalDateTime.now(),
        LocalDateTime.now().plus(60, ChronoUnit.MINUTES), 6,
        Location.INDOOR, null));
  }

  @Test
  void shouldFindAllRuns() {
    List<Run> runs = repository.findAll();
    assertEquals(2, runs.size(), "Should find 2 runs");
  }
}
