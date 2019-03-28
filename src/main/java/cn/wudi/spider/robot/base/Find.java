package cn.wudi.spider.robot.base;

import cn.wudi.spider.http.Request;
import cn.wudi.spider.service.logger.ILogger;

/**
 * @author wudi
 */
public class Find extends AbstractFind {

  public Request request() {
    return new Request().client(getClient());
  }

  public ILogger logger() {
    return getLogger();
  }
}
