package com.vhsdev.runnerz.run;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {

  /**
   * With Spring being IoC, this would be the wrong way to go about accessing the repository.
   * This is an issue because me and 800 friends could hit this controller and we'll get a new
   * instance of the RunRepository each time. Instead we want to treat RunRepository as a singleton.
   *
   * @see https://www.youtube.com/watch?v=31KTdfRH6nY (1:01:11) for dependency injection info.
   */
  private final RunRepository repository;

  public RunController() {
    this.repository = new RunRepository();
  }

  List<Run> findAll() {
    return null;
  }
}
