package cn.wudi.spider.service.client;

import cn.wudi.spider.http.IClient;

/**
 * @author wudi
 */
public interface HttpClientFactory {

  IClient newClient(String session);
}
