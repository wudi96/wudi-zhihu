package cn.wudi.spider.robot.base;

import cn.wudi.spider.http.IClient;
import cn.wudi.spider.logger.ILogger;

/**
 * @author wudi
 */
public abstract class AbstractFind {

  private ILogger logger;

  private IClient client;

  public IClient getClient() {
    return client;
  }

  public ILogger getLogger() {
    return logger;
  }


  public AbstractFind setLogger(ILogger logger) {
    this.logger = logger;
    return this;
  }

  public AbstractFind setClient(IClient client) {
    this.client = client;
    return this;
  }
}
