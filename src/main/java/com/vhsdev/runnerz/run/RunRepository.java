package com.vhsdev.runnerz.run;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class RunRepository {

  private List<Run> runs = new ArrayList<>();

  List<Run> findAll() {
    return runs;
  }

  Optional<Run> findById(Integer id) {
    return runs.stream()
        .filter(run -> run.id().equals(id))
        .findFirst();
  }

  @PostConstruct
  private void init() {
    runs.add(new Run(1, "Morning Run", LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 5, Location.INDOOR));
    runs.add(new Run(2, "Evening Run", LocalDateTime.now(),
        LocalDateTime.now().plus(1, ChronoUnit.HOURS), 10, Location.OUTDOOR));
  }
}
