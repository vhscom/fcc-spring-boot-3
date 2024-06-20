package com.vhsdev.runnerz.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfig {

  // Uncomment this method to use the ThrottledVirtualThreadsExecutor class
  // This method creates a ThrottledVirtualThreadsExecutor bean with N virtual threads
  @Bean(name = "virtualThreadExecutor")
  public ThrottledVirtualThreadsExecutor virtualThreadExecutor() {
    return new ThrottledVirtualThreadsExecutor(10); // 10 virtual threads
  }

  // Uncomment this method to use the newVirtualThreadPerTaskExecutor method
  // This method creates a virtualThreadExecutor bean with a new virtual thread per task
  // @Bean(name = "virtualThreadExecutor")
  // public ExecutorService virtualThreadExecutor() {
  //   return Executors.newVirtualThreadPerTaskExecutor();
  // }
}
