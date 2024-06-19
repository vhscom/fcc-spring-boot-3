package com.vhsdev.runnerz.run;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class HelloVirtualThreadController {

  private static final Logger log = LoggerFactory.getLogger(HelloVirtualThreadController.class);

  @GetMapping("/api/threads")
  public String helloVirtualThread() throws InterruptedException, ExecutionException {
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      return runInVirtualThread(executor, () -> {
        Thread.sleep(1000);
        return "hello virtual thread";
      });
    } catch (InterruptedException | ExecutionException e) {
      throw new VirtualThreadException();
    }
  }

  private String runInVirtualThread(ExecutorService executor, Callable<String> task)
      throws InterruptedException, ExecutionException {
    return executor.submit(task).get();
  }

  @ExceptionHandler({VirtualThreadException.class, InterruptedException.class, ExecutionException.class})
  public ResponseEntity<String> handleException(Exception e) {
    log.error("Error occurred while executing task", e);
    return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
  }
}
