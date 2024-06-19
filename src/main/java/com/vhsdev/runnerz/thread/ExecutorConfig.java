package com.vhsdev.runnerz.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfig {

  @Bean(name = "virtualThreadExecutor")
  public Executor virtualThreadExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }
}
