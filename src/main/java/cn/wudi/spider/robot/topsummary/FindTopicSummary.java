package cn.wudi.spider.robot.topsummary;

import static cn.wudi.spider.constant.Constant.TOP_PREFIX;
import static cn.wudi.spider.constant.Constant.TOP_SUFFIX;

import cn.wudi.spider.robot.base.Find;

/**
 * @author wudi
 */
public class FindTopicSummary extends Find {


  public String findTopAnswersNum(String topicId) {
    return "888";
  }

  public String findTopUrl(String topicId) {
    return TOP_PREFIX + topicId + TOP_SUFFIX;
  }
}
