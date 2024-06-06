package com.vhsdev.runnerz.run;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {

  /**
   * Controllers are dumb so we could do this in the controller but we're not going to.
   * Instead of doing this we're going to split this code into a service and delegate the
   * responsibility of managing the runs starting with a RunRepository.
   */
  private List<Run> runs = new ArrayList<>();

  @GetMapping("/hello")
  String home() {
    return "Hello runnerz!";
  }
}
