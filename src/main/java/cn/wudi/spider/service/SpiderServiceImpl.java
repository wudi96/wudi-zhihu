package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.CreateResult;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.SummaryQuery;
import cn.wudi.spider.entity.SummaryResult;
import cn.wudi.spider.logger.support.LocalLoggerFactory;
import cn.wudi.spider.robot.base.AbstractFind;
import cn.wudi.spider.robot.base.DefaultHttpClientFactory;
import cn.wudi.spider.robot.base.HttpClientFactory;
import cn.wudi.spider.robot.topid.FindTopicId;
import cn.wudi.spider.robot.topsummary.FindTopicSummary;
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
    CreateResult createResult = new CreateResult();
    String topicTitle = query.getTopicTitle();
    FindTopicId findTopicId = new FindTopicId();

    createLoggerAndClient(findTopicId, topicTitle);

    createResult.setTopicTitle(topicTitle);
    createResult.setTopicId(findTopicId.findTopId(topicTitle));
    return Result.ok(createResult);
  }

  @Override
  public <T extends CommonQuery> Result summary(T query) {
    SummaryResult summaryResult = new SummaryResult();
    String topicId = ((SummaryQuery) query).getTopicId();
    FindTopicSummary findTopicSummary = new FindTopicSummary();

    createLoggerAndClient(findTopicSummary, topicId);

    summaryResult.setTopicTitle(query.getTopicTitle());
    summaryResult.setTopicId(topicId);
    summaryResult.setTopicAnswerNum(findTopicSummary.findTopAnswersNum(topicId));
    summaryResult.setTopicUrl(findTopicSummary.findTopUrl(topicId));
    return Result.ok(summaryResult);
  }

  private void createLoggerAndClient(AbstractFind fund, String id) {
    fund.setClient(httpClientFactory.getClient(id));
    fund.setLogger(localLoggerFactory.newLogger(id));
  }
}
