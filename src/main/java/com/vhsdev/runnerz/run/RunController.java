package com.vhsdev.runnerz.run;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {

  private final RunRepository repository;
  private final RunRepository runRepository;

  public RunController(RunRepository runRepository) {
    this.repository = runRepository;
    this.runRepository = runRepository;
  }

  @GetMapping("/api/runs")
  List<Run> findAll() {
    return runRepository.findAll();
  }
}
