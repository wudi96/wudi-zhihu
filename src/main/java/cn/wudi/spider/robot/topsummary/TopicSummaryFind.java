package cn.wudi.spider.robot.topsummary;

import static cn.wudi.spider.constant.Constant.TOP_PREFIX;
import static cn.wudi.spider.constant.Constant.TOP_SUFFIX;

import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.SummaryResult;
import cn.wudi.spider.robot.base.Find;

/**
 * @author wudi
 */
public class TopicSummaryFind extends Find {

  public Result<SummaryResult> findTopicSummary(String topicTitle, String topicId) {
    SummaryResult summaryResult = new SummaryResult();
    summaryResult.setTopicTitle(topicTitle);
    summaryResult.setTopicId(topicId);
    summaryResult.setTopicAnswerNum("888");
    summaryResult.setTopicUrl(TOP_PREFIX + topicId + TOP_SUFFIX);
    return Result.ok(summaryResult);
  }
}
