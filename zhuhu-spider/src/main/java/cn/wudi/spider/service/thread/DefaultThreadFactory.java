package cn.wudi.spider.service.thread;

import cn.wudi.spider.robot.scheduler.Task;
import java.util.concurrent.ThreadFactory;

/**
 * @author wudi
 */
public class DefaultThreadFactory implements ThreadFactory {

  @Override
  public Thread newThread(Runnable r) {
    Task task = (Task) r;
    return new Thread(r, task.getThreadName());
  }
}
