package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.CrawlerResult;
import cn.wudi.spider.entity.CreateResult;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.Status;
import cn.wudi.spider.robot.TopicCrawlerFind;
import cn.wudi.spider.robot.TopicIdFind;
import cn.wudi.spider.robot.TopicSummaryFind;
import cn.wudi.spider.robot.base.AbstractFind;
import cn.wudi.spider.robot.scheduler.Task;
import cn.wudi.spider.service.client.DefaultHttpClientFactory;
import cn.wudi.spider.service.client.HttpClientFactory;
import cn.wudi.spider.service.context.ContextFactory;
import cn.wudi.spider.service.context.DefaultContextFactory;
import cn.wudi.spider.service.logger.support.LocalLoggerFactory;
import cn.wudi.spider.service.redis.TopicTitleRedis;
import cn.wudi.spider.service.thread.DefaultThreadFactory;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wudi
 */
@Service
@Slf4j
public class SpiderServiceImpl implements SpiderService {

  private final HttpClientFactory httpClientFactory;
  private final LocalLoggerFactory localLoggerFactory;
  private final ContextFactory contextFactory;
  private final TopicTitleRedis topicTitleRedis;
  private final ThreadPoolExecutor executor;

  @Autowired
  public SpiderServiceImpl(TopicTitleRedis topicTitleRedis) {
    this.httpClientFactory = new DefaultHttpClientFactory();
    this.contextFactory = new DefaultContextFactory();
    this.localLoggerFactory = new LocalLoggerFactory();
    this.localLoggerFactory.setHomePath("./logs");
    this.topicTitleRedis = topicTitleRedis;
    this.executor = new ThreadPoolExecutor(5, 10, 5000, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<>(5), new DefaultThreadFactory());
  }

  @Override
  public <T extends CommonQuery> Result create(T query) {
    String topicId = topicTitleRedis.get(query);
    if (topicId != null) {
      return Result.ok(new CreateResult()
          .setTopicTitle(query.getTopicTitle())
          .setTopicId(topicId));
    }
    TopicIdFind topicIdFind = new TopicIdFind();
    createLoggerAndClient(topicIdFind, query);
    Result<CreateResult> result = topicIdFind.findTopId();
    CreateResult createResult = result.getData();
    topicTitleRedis.set(createResult);
    String resultTopicTitle = createResult.getTopicTitle();
    String topicTitleQuery = query.getTopicTitle();
    if (!resultTopicTitle.equals(topicTitleQuery)) {
      topicTitleRedis
          .set(new CreateResult().setTopicTitle(topicTitleQuery)
              .setTopicId(createResult.getTopicId()));
    }
    return result;
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
    CrawlerResult crawlerResult = new CrawlerResult();
    crawlerResult.setStatus(Status.RUSHING);
    Task task = topicCrawlerFind.createTask(crawlerResult);
    Thread thread = executor.getThreadFactory().newThread(task);
    thread.start();
    return Result.ok(crawlerResult);
  }

  private void createLoggerAndClient(AbstractFind fund, CommonQuery query) {
    fund.setClient(httpClientFactory.newClient(query.getTopicTitle()));
    fund.setLogger(localLoggerFactory.newLogger(query.getTopicTitle()));
    fund.setContext(contextFactory.newContext(query));
  }
}
