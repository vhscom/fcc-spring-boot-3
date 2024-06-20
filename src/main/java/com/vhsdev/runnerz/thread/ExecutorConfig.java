package com.vhsdev.runnerz.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfig {

  private static final Logger log = LoggerFactory.getLogger(ExecutorConfig.class);

  // This method returns the number of cores available in the system
  static int numberOfCores() {
    log.info("Number of cores: {}", Runtime.getRuntime().availableProcessors());
    return Runtime.getRuntime().availableProcessors();
  }

  // Uncomment this method to use the ThrottledVirtualThreadsExecutor class
  // This method creates a ThrottledVirtualThreadsExecutor bean with N virtual threads
  @Bean(name = "virtualThreadExecutor")
  public ThrottledVirtualThreadsExecutor virtualThreadExecutor() {
    return new ThrottledVirtualThreadsExecutor(numberOfCores());
  }

  // Uncomment this method to use the newVirtualThreadPerTaskExecutor method
  // This method creates a virtualThreadExecutor bean with a new virtual thread per task
  // @Bean(name = "virtualThreadExecutor")
  // public ExecutorService virtualThreadExecutor() {
  //   return Executors.newVirtualThreadPerTaskExecutor();
  // }
}
