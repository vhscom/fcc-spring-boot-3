package com.vhsdev.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;


public record Run(
  Integer id,
  @NotEmpty
  String title,
  LocalDateTime startedOn,
  LocalDateTime completedOn,
  @Positive
  Integer miles,
  Location location
) {
  public Run {
    if (startedOn.isAfter(completedOn)) {
      throw new IllegalArgumentException("Run must be completed after it has started");
    }
  }
}
