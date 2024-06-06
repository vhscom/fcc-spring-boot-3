package com.vhsdev.runnerz.run;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.beans.factory.parsing.Location;

/**
 * None of this is necessary with Spring Boot. Example added using Generate features
 * and a little Copilot while following https://www.youtube.com/watch?v=31KTdfRH6nY.
 *
 * At 44:09 in the video, the presenter shows none of this is necessary with Record.
 */
public class RunClazz {
  private Integer id;
  private String title;
  private LocalDateTime startedOn;
  private LocalDateTime completedOn;
  private Integer miles;
  private Location location;

  public RunClazz(Integer id, String title, LocalDateTime startedOn, LocalDateTime completedOn,
      Integer miles, Location location) {
    this.id = id;
    this.title = title;
    this.startedOn = startedOn;
    this.completedOn = completedOn;
    this.miles = miles;
    this.location = location;
    if (startedOn.isAfter(completedOn)) {
      throw new IllegalArgumentException("The run cannot be completed before it starts");
    }
  }

  public Duration getDuration() {
    return Duration.between(startedOn, completedOn);
  }

  public Integer getAvgPace() {
    return Math.toIntExact(getDuration().toMinutes() / miles);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDateTime getStartedOn() {
    return startedOn;
  }

  public void setStartedOn(LocalDateTime startedOn) {
    this.startedOn = startedOn;
  }

  public LocalDateTime getCompletedOn() {
    return completedOn;
  }

  public void setCompletedOn(LocalDateTime completedOn) {
    this.completedOn = completedOn;
  }

  public Integer getMiles() {
    return miles;
  }

  public void setMiles(Integer miles) {
    this.miles = miles;
  }

  public Location getLocation() {
    return location;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunClazz run = (RunClazz) o;
    return Objects.equals(id, run.id) && Objects.equals(title, run.title)
        && Objects.equals(startedOn, run.startedOn) && Objects.equals(completedOn,
        run.completedOn) && Objects.equals(miles, run.miles) && Objects.equals(
        location, run.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, startedOn, completedOn, miles, location);
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
