package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.SummaryQuery;
import cn.wudi.spider.service.logger.support.LocalLoggerFactory;
import cn.wudi.spider.robot.base.AbstractFind;
import cn.wudi.spider.service.client.DefaultHttpClientFactory;
import cn.wudi.spider.service.client.HttpClientFactory;
import cn.wudi.spider.robot.topid.TopicIdFind;
import cn.wudi.spider.robot.topsummary.TopicSummaryFind;
import org.springframework.stereotype.Service;

/**
 * @author wudi
 */
@Service
public class SpiderServiceImpl implements SpiderService {

  private final HttpClientFactory httpClientFactory;
  private final LocalLoggerFactory localLoggerFactory;

  public SpiderServiceImpl() {
    this.httpClientFactory = new DefaultHttpClientFactory();
    this.localLoggerFactory = new LocalLoggerFactory();
    this.localLoggerFactory.setHomePath("./logs");
  }

  @Override
  public <T extends CommonQuery> Result create(T query) {
    String topicTitle = query.getTopicTitle();
    TopicIdFind topicIdFind = new TopicIdFind();
    createLoggerAndClient(topicIdFind, topicTitle);
    return topicIdFind.findTopId(topicTitle);
  }

  @Override
  public <T extends CommonQuery> Result summary(T query) {
    String topicId = ((SummaryQuery) query).getTopicId();
    String topicTitle = query.getTopicTitle();
    TopicSummaryFind topicSummaryFind = new TopicSummaryFind();
    createLoggerAndClient(topicSummaryFind, topicId);
    return topicSummaryFind.findTopicSummary(topicTitle, topicId);
  }

  @Override
  public <T extends CommonQuery> Result crawler(T query) {
    return null;
  }

  private void createLoggerAndClient(AbstractFind fund, String id) {
    fund.setClient(httpClientFactory.newClient(id));
    fund.setLogger(localLoggerFactory.newLogger(id));
  }
}
