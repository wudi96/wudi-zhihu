package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.robot.base.AbstractFind;
import cn.wudi.spider.robot.topcrawler.TopicCrawlerFind;
import cn.wudi.spider.robot.topid.TopicIdFind;
import cn.wudi.spider.robot.topsummary.TopicSummaryFind;
import cn.wudi.spider.service.client.DefaultHttpClientFactory;
import cn.wudi.spider.service.client.HttpClientFactory;
import cn.wudi.spider.service.context.ContextFactory;
import cn.wudi.spider.service.context.DefaultContextFactory;
import cn.wudi.spider.service.logger.support.LocalLoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author wudi
 */
@Service
public class SpiderServiceImpl implements SpiderService {

  private final HttpClientFactory httpClientFactory;
  private final LocalLoggerFactory localLoggerFactory;
  private final ContextFactory contextFactory;

  public SpiderServiceImpl() {
    this.httpClientFactory = new DefaultHttpClientFactory();
    this.contextFactory = new DefaultContextFactory();
    this.localLoggerFactory = new LocalLoggerFactory();
    this.localLoggerFactory.setHomePath("./logs");
  }

  @Override
  public <T extends CommonQuery> Result create(T query) {
    TopicIdFind topicIdFind = new TopicIdFind();
    createLoggerAndClient(topicIdFind, query);
    return topicIdFind.findTopId();
  }

  @Override
  public <T extends CommonQuery> Result summary(T query) {
    TopicSummaryFind topicSummaryFind = new TopicSummaryFind();
    createLoggerAndClient(topicSummaryFind, query);
    return topicSummaryFind.findTopicSummary();
  }

  @Override
  public <T extends CommonQuery> Result crawler(T query) {
    TopicCrawlerFind topicCrawlerFind = new TopicCrawlerFind();
    createLoggerAndClient(topicCrawlerFind, query);
    return topicCrawlerFind.findTopicContext();
  }

  private void createLoggerAndClient(AbstractFind fund, CommonQuery query) {
    fund.setClient(httpClientFactory.newClient(query.getTopicTitle()));
    fund.setLogger(localLoggerFactory.newLogger(query.getTopicTitle()));
    fund.setContext(contextFactory.newContext(query));
  }
}
