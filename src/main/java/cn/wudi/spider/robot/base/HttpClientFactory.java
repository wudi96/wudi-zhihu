package cn.wudi.spider.robot.base;

import cn.wudi.spider.http.IClient;

/**
 * @author wudi
 */
public interface HttpClientFactory {

  IClient newClient(String session);
}
