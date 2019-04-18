package cn.wudi.spider.service.logger;

import java.io.Closeable;

/**
 * @author wudi 本地日志
 */
public interface ILogger extends Closeable {

  /**
   * 日志名称
   */
  String name();

  default ILogger info(String format, Object... arguments) {
    return this;
  }

  default ILogger error(String format, Object... arguments) {
    return this;
  }

  /**
   * @param msg 日志信息
   */
  ILogger println(String msg);

  /**
   * @param throwable 异常
   */
  ILogger println(Throwable throwable);

  /**
   * 关闭输入
   */
  @Override
  default void close() {
  }

  /**
   * @param name 文件名称
   * @param msg 文件内容
   */
  ILogger writeFile(String name, String msg);

  /**
   * @param name 文件名称
   * @param bytes 文件内容
   */
  ILogger writeFile(String name, byte[] bytes);
}
