package com.vhsdev.runnerz.run;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class VirtualThreadException extends RuntimeException {

  public VirtualThreadException() {
    super("Error occurred while executing task");
  }
}
