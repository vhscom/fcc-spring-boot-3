package com.vhsdev.runnerz.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class HelloVirtualThreadController {

  private static final Logger log = LoggerFactory.getLogger(HelloVirtualThreadController.class);

  @GetMapping("/threads")
  public String helloVirtualThread() throws InterruptedException {
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      return submitTaskAndGetResult(executor, () -> {
        Thread.sleep(1000);
        return "hello virtual thread";
      });
    } catch (ExecutionException e) {
      log.error("Error occurred while executing task", e);
      throw new VirtualThreadException();
    }
  }

  private String submitTaskAndGetResult(ExecutorService executor, Callable<String> task)
      throws InterruptedException, ExecutionException {
    return executor.submit(task).get();
  }

  @ExceptionHandler(VirtualThreadException.class)
  public ResponseEntity<String> handleException(Exception e) {
    log.error("Virtual thread exception occurred", e);
    String errorMessage = "Error occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage();
    return ResponseEntity.status(500).body(errorMessage);
  }
}
