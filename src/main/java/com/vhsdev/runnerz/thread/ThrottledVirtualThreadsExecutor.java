package com.vhsdev.runnerz.thread;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ThrottledVirtualThreadsExecutor implements ExecutorService {
  private final Semaphore semaphore;
  private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

  public ThrottledVirtualThreadsExecutor(int concurrencyLevel, boolean fair) {
    this.semaphore = new Semaphore(concurrencyLevel, fair);
  }

  public ThrottledVirtualThreadsExecutor(int concurrencyLevel) {
    this(concurrencyLevel, false);
  }

  @Override
  public void close() {
    executor.close();
  }

  @Override
  public void execute(Runnable command) {
    executor.execute(new ThrottledRunnable(semaphore, command));
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return executor.submit(new ThrottledCallable<>(semaphore, task));
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    return executor.submit(new ThrottledRunnable(semaphore, task), result);
  }

  @Override
  public Future<?> submit(Runnable task) {
    return executor.submit(new ThrottledRunnable(semaphore, task));
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
    return executor.invokeAll(tasks.stream().map(task -> new ThrottledCallable<>(semaphore, task)).toList());
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
    return executor.invokeAll(tasks.stream().map(task -> new ThrottledCallable<>(semaphore, task)).toList(), timeout, unit);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
    return executor.invokeAny(tasks.stream().map(task -> new ThrottledCallable<>(semaphore, task)).toList());
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return executor.invokeAny(tasks.stream().map(task -> new ThrottledCallable<>(semaphore, task)).toList(), timeout, unit);
  }

  @Override
  public void shutdown() {
    executor.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow() {
    return executor.shutdownNow().stream().map(runnable -> {
      if (runnable instanceof ThrottledRunnable throttledRunnable) {
        throttledRunnable.interrupt();
        return throttledRunnable.runnable();
      } else {
        return runnable;
      }
    }).toList();
  }

  @Override
  public boolean isShutdown() {
    return executor.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return executor.isTerminated();
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return executor.awaitTermination(timeout, unit);
  }

  private static class ThrottledRunnable implements Runnable {
    private final Semaphore semaphore;
    private final Runnable runnable;
    private boolean interrupted;

    ThrottledRunnable(Semaphore semaphore, Runnable runnable) {
      this.semaphore = semaphore;
      this.runnable = runnable;
    }

    void interrupt() {
      this.interrupted = true;
    }

    Runnable runnable() {
      return this.runnable;
    }

    @Override
    public void run() {
      try {
        semaphore.acquire();
        try {
          if (!interrupted) {
            runnable.run();
          }
        } finally {
          semaphore.release();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private static class ThrottledCallable<T> implements Callable<T> {
    private final Semaphore semaphore;
    private final Callable<T> callable;

    ThrottledCallable(Semaphore semaphore, Callable<T> callable) {
      this.semaphore = semaphore;
      this.callable = callable;
    }

    @Override
    public T call() throws Exception {
      try {
        semaphore.acquire();
        try {
          return callable.call();
        } finally {
          semaphore.release();
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw e;
      }
    }
  }
}
