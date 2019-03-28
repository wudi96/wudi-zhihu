package cn.wudi.spider.logger.support;

import cn.wudi.spider.logger.ILogger;

/**
 * @author wudi
 */
public class LoggerProxy implements ILogger {

  private final transient ILogger logger;

  LoggerProxy(String name, String homePath) {
    if (homePath == null || "".equals(homePath)) {
      this.logger = new EmptyLogger(name);
      return;
    }
    this.logger = new LocalLogger(name, homePath);
  }

  @Override
  public String name() {
    return logger.name();
  }

  @Override
  public ILogger info(String format, Object... arguments) {
    return logger.info(format, arguments);
  }

  @Override
  public ILogger error(String format, Object... arguments) {
    return logger.error(format, arguments);
  }

  @Override
  public ILogger println(String msg) {
    return logger.println(msg);
  }

  @Override
  public ILogger println(Throwable throwable) {
    return logger.println(throwable);
  }

  @Override
  public ILogger writeFile(String name, String msg) {
    return logger.writeFile(name, msg);
  }

  @Override
  public ILogger writeFile(String name, byte[] bytes) {
    return logger.writeFile(name, bytes);
  }

  @Override
  public void close() {
    logger.close();
  }
}
