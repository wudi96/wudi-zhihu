package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.CreateResult;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.SummaryQuery;
import cn.wudi.spider.entity.SummaryResult;
import cn.wudi.spider.robot.topid.FindTopicId;
import cn.wudi.spider.robot.topsummary.FindTopicSummary;

/**
 * @author wudi
 */
public class SpiderServiceImpl implements SpiderService {

  @Override
  public <T extends CommonQuery> Result create(T query) {
    CreateResult createResult = new CreateResult();
    createResult.setTopicTitle(query.getTopicTitle());
    createResult.setTopicId(new FindTopicId().findTopId(query.getTopicTitle()));
    return Result.ok(createResult);
  }

  @Override
  public <T extends CommonQuery> Result summary(T query) {
    SummaryResult summaryResult = new SummaryResult();
    String topicId = ((SummaryQuery) query).getTopicId();
    FindTopicSummary findTopicSummary = new FindTopicSummary();
    summaryResult.setTopicTitle(query.getTopicTitle());
    summaryResult.setTopicId(topicId);
    summaryResult.setTopicAnswerNum(findTopicSummary.findTopAnswersNum(topicId));
    summaryResult.setTopicUrl(findTopicSummary.findTopUrl(topicId));
    return Result.ok(summaryResult);
  }
}
