package com.vhsdev.runnerz.run;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/runs")
public class RunController {

  private final RunRepository runRepository;

  public RunController(RunRepository runRepository) {
    this.runRepository = runRepository;
  }

  @GetMapping("")
  List<Run> findAll() {
    return runRepository.findAll();
  }

  @GetMapping("/{id}")
  Run findById(@PathVariable Integer id) {
    return runRepository.findById(id);
  }
}
