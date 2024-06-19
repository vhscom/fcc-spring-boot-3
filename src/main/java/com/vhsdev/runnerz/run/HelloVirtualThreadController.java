package com.vhsdev.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class HelloVirtualThreadController {

  private static final Logger log = LoggerFactory.getLogger(HelloVirtualThreadController.class);


  @GetMapping("/api/threads")
  public String helloVirtualThread() {
    try {
      return runInVirtualThread(() -> {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          log.info("Thread interrupted");
        }
        return "hello virtual thread";
      });
    } catch (InterruptedException | ExecutionException e) {
      log.error("Error occurred", e);
      return "error occurred";
    }
  }

  private String runInVirtualThread(Callable<String> task)
      throws InterruptedException, ExecutionException {
    var executor = Executors.newVirtualThreadPerTaskExecutor();
    Future<String> future = executor.submit(task);
    String result = future.get();
    executor.shutdown();
    return result;
  }
}
