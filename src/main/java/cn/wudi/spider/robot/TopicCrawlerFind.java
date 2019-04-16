package cn.wudi.spider.robot;

import cn.wudi.spider.robot.base.Find;
import cn.wudi.spider.robot.scheduler.Task;
import cn.wudi.spider.service.mongo.TopicContentMongo;

/**
 * @author wudi
 */
public class TopicCrawlerFind extends Find {

  public Task createTask(TopicContentMongo topicContentMongo) {
    String topicTitle = topicTitle();
    String topicId = topicId();
    String topicAnswerNum = topicAnswerNum();
    return new Task(this, topicContentMongo, topicTitle, topicId, topicAnswerNum);
  }
}
