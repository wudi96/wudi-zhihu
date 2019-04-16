package cn.wudi.spider.robot;

import cn.wudi.spider.robot.base.Find;
import cn.wudi.spider.robot.scheduler.Task;
import cn.wudi.spider.service.mongo.TopicContentMongo;
import java.util.HashMap;

/**
 * @author wudi
 */
public class TopicCrawlerFind extends Find {

  public Task createTask(TopicContentMongo topicContentMongo,
      HashMap<String, String> threadMap) {
    String topicTitle = topicTitle();
    String topicId = topicId();
    String topicAnswerNum = topicAnswerNum();
    return new Task(this, topicContentMongo, threadMap, topicTitle, topicId, topicAnswerNum);
  }
}
