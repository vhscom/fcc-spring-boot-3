package com.vhsdev.runnerz.run;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRunRepository {

  private static final Logger log = LoggerFactory.getLogger(InMemoryRunRepository.class);

  private List<Run> runs = new ArrayList<>();

  List<Run> findAll() {
    return runs;
  }

  Optional<Run> findById(Integer id) {
    return Optional.ofNullable(runs.stream()
        .filter(run -> run.id().equals(id))
        .findFirst()
        .orElseThrow(RunNotFoundException::new));
  }

  void create(Run run) {
    Run newRun = new Run(run.id(), run.title(), run.startedOn(), run.completedOn(),
        run.miles(), run.location());
    runs.add(newRun);
  }

  public void update(Run run, Integer id) {
    Optional<Run> existingRun = findById(id);
    existingRun.ifPresent(value -> runs.set(runs.indexOf(value), run));
  }

  public void delete(Integer id) {
    findById(id).ifPresent(runs::remove);
  }

  @PostConstruct
  private void init() {
    runs.add(new Run(1, "Morning Run", LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 5, Location.INDOOR));
    runs.add(new Run(2, "Evening Run", LocalDateTime.now(),
        LocalDateTime.now().plus(1, ChronoUnit.HOURS), 10, Location.OUTDOOR));
  }
}
