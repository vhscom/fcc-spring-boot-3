package com.vhsdev.runnerz.run;

import java.time.LocalDateTime;


public record Run(
  Integer id,
  String title,
  LocalDateTime startedOn,
  LocalDateTime completedOn,
  Integer miles,
  Location location
) {
  public Run {
    if (startedOn.isAfter(completedOn)) {
      throw new IllegalArgumentException("Run must be completed after it has started");
    }
    if (miles < 0) {
      throw new IllegalArgumentException("Miles must be greater than or equal to zero");
    }
  }
}
