package cn.wudi.spider.service.logger.support;

import cn.wudi.spider.service.logger.ILogger;

/**
 * @author wudi
 */
public class EmptyLogger implements ILogger {

  private final String name;

  EmptyLogger(String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public ILogger println(String msg) {
    return this;
  }

  @Override
  public ILogger println(Throwable throwable) {
    return this;
  }

  @Override
  public ILogger writeFile(String name, String msg) {
    return this;
  }

  @Override
  public ILogger writeFile(String name, byte[] bytes) {
    return this;
  }
}
