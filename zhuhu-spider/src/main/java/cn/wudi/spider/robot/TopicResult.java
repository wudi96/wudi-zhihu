package cn.wudi.spider.robot;

import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.topic.Topic;
import cn.wudi.spider.entity.topic.TopicContent;
import cn.wudi.spider.robot.base.Find;
import cn.wudi.spider.service.mongo.TopicContentMongo;
import java.util.List;

/**
 * @author wudi
 */
public class TopicResult extends Find {

  public Result result(TopicContentMongo topicContentMongo) {
    List<TopicContent> topicContents = topicContentMongo.get(topicId());
    Topic topic = new Topic();
    topic.setTopic(topicTitle());
    topic.setTopicContents(topicContents);
    return Result.ok(topic);
  }
}
