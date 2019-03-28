package cn.wudi.spider.logger;

/**
 * @author wudi 日志工厂
 */
public interface ILoggerFactory {

  /**
   * @param name 日志对象的名称
   */
  ILogger newLogger(String name);
}
