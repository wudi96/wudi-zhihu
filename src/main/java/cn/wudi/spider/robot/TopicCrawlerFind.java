package cn.wudi.spider.robot;

import cn.wudi.spider.entity.CrawlerResult;
import cn.wudi.spider.robot.base.Find;
import cn.wudi.spider.robot.scheduler.Task;

/**
 * @author wudi
 */
public class TopicCrawlerFind extends Find {

  public Task createTask(CrawlerResult crawlerResult) {
    String topicTitle = topicTitle();
    String topicId = topicId();
    String topicAnswerNum = topicAnswerNum();
    return new Task(this, crawlerResult, topicTitle, topicId, topicAnswerNum);
  }
}
