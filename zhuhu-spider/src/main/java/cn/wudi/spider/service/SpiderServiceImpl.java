package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.CrawlerResult;
import cn.wudi.spider.entity.CreateResult;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.ResultType;
import cn.wudi.spider.entity.Status;
import cn.wudi.spider.robot.TopicCrawlerFind;
import cn.wudi.spider.robot.TopicIdFind;
import cn.wudi.spider.robot.TopicResult;
import cn.wudi.spider.robot.TopicStatus;
import cn.wudi.spider.robot.TopicSummaryFind;
import cn.wudi.spider.robot.base.AbstractFind;
import cn.wudi.spider.robot.scheduler.Task;
import cn.wudi.spider.service.client.DefaultHttpClientFactory;
import cn.wudi.spider.service.client.HttpClientFactory;
import cn.wudi.spider.service.context.ContextFactory;
import cn.wudi.spider.service.context.DefaultContextFactory;
import cn.wudi.spider.service.logger.support.LocalLoggerFactory;
import cn.wudi.spider.service.mongo.TopicContentMongo;
import cn.wudi.spider.service.redis.TopicTitleRedis;
import cn.wudi.spider.service.thread.DefaultThreadFactory;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
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
  private final TopicContentMongo topicContentMongo;

  private HashMap<String, String> threadMap = new HashMap<>();

  @Autowired
  public SpiderServiceImpl(TopicTitleRedis topicTitleRedis,
      TopicContentMongo topicContentMongo) {
    this.topicContentMongo = topicContentMongo;
    this.httpClientFactory = new DefaultHttpClientFactory();
    this.contextFactory = new DefaultContextFactory();
    this.localLoggerFactory = new LocalLoggerFactory();
    this.localLoggerFactory.setHomePath("./logs");
    this.topicTitleRedis = topicTitleRedis;
    this.executor = new ThreadPoolExecutor(5, 10, 5000, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(Integer.MAX_VALUE), new DefaultThreadFactory());
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
    Result result = topicIdFind.findTopId();
    if (result.getReturnCode().equals(ResultType.ERR.getCode())) {
      return result;
    }
    CreateResult createResult = (CreateResult) result.getData();
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
  public <T extends CommonQuery> Result result(T query) {
    TopicResult topicResult = new TopicResult();
    createLoggerAndClient(topicResult, query);
    return topicResult.result(topicContentMongo);
  }

  @Override
  public <T extends CommonQuery> Result status(T query) {
    TopicStatus topicStatus = new TopicStatus();
    createLoggerAndClient(topicStatus, query);
    return topicStatus.status(threadMap);
  }

  @Override
  public <T extends CommonQuery> Result crawler(T query) {
    TopicCrawlerFind topicCrawlerFind = new TopicCrawlerFind();
    createLoggerAndClient(topicCrawlerFind, query);
    CrawlerResult crawlerResult = new CrawlerResult();
    crawlerResult.setStatus(Status.RUSHING);
    Task task = topicCrawlerFind.createTask(topicContentMongo, threadMap);
    threadMap.put(task.getThreadName(), Status.RUSHING);

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
