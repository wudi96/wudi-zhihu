package cn.wudi.spider.service.client;

import cn.wudi.spider.http.IClient;

/**
 * @author wudi
 */
public class DefaultHttpClientFactory implements HttpClientFactory {

  @Override
  public IClient newClient(String session) {
    return IClient.newBuilder()
        .session(session)
        .verbose(false)
        .insecureSkipVerify(true)
        .build();
  }
}
