package cn.wudi.spider.service.thread;

import cn.wudi.spider.robot.scheduler.Task;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wudi
 */
public class DefaultThreadFactory implements ThreadFactory {

  private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
  private final ThreadGroup group;
  private final AtomicInteger threadNumber = new AtomicInteger(1);
  private final String namePrefix;

  public DefaultThreadFactory() {
    SecurityManager s = System.getSecurityManager();
    group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    namePrefix = "pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
  }

  /**
   * 用于创建一个线程
   */
  @Override
  public Thread newThread(Runnable r) {
    Thread t = new Thread(group, r,
        namePrefix + threadNumber.getAndIncrement(),
        0);
    if (t.isDaemon()) {
      t.setDaemon(false);
    }
    if (t.getPriority() != Thread.NORM_PRIORITY) {
      t.setPriority(Thread.NORM_PRIORITY);
    }
    return t;
  }
}
