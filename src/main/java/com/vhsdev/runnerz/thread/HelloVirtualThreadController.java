package com.vhsdev.runnerz.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloVirtualThreadController {

  private static final Logger log = LoggerFactory.getLogger(HelloVirtualThreadController.class);

  private final ExecutorService virtualThreadExecutor;

  public HelloVirtualThreadController(@Qualifier("virtualThreadExecutor") Executor virtualThreadExecutor) {
    this.virtualThreadExecutor = (ExecutorService) virtualThreadExecutor; // Ensure cast to ExecutorService
  }

  @GetMapping("/threads")
  @Async("virtualThreadExecutor")
  public CompletableFuture<String> helloVirtualThread() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        return submitTaskAndGetResult(() -> {
          Thread.sleep(1000); // Simulate some task that takes time
          return "hello virtual thread";
        });
      } catch (ExecutionException | InterruptedException e) {
        log.error("Error occurred while executing task", e);
        throw new VirtualThreadException();
      }
    }, virtualThreadExecutor);
  }

  private String submitTaskAndGetResult(Callable<String> task) throws InterruptedException, ExecutionException {
    return virtualThreadExecutor.submit(task).get();
  }

  @ExceptionHandler(VirtualThreadException.class)
  public ResponseEntity<String> handleException(Exception e) {
    log.error("Virtual thread exception occurred", e);
    String errorMessage = "Error occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage();
    return ResponseEntity.status(500).body(errorMessage);
  }
}
