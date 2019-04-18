package cn.wudi.spider.service.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wudi
 */
public class Once {

  private final Lock lock = new ReentrantLock();
  private final AtomicInteger done = new AtomicInteger();

  public Once() {
  }

  public void doRun(Runnable runnable) {
    if (this.done.get() != 1) {
      try {
        this.lock.lock();
        if (this.done.get() == 0) {
          try {
            runnable.run();
          } finally {
            this.done.set(1);
          }
        }
      } finally {
        this.lock.unlock();
      }

    }
  }
}
