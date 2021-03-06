package cn.wudi.spider.service.logger.support;

import cn.wudi.spider.service.logger.ILogger;
import cn.wudi.spider.service.logger.ILoggerFactory;

/**
 * @author wudi
 */
public class LocalLoggerFactory implements ILoggerFactory {

  private String homePath;

  @Override
  public ILogger newLogger(String name) {
    return new LoggerProxy(name, homePath);
  }

  public String getHomePath() {
    return homePath;
  }

  public void setHomePath(String homePath) {
    this.homePath = homePath;
  }
}
