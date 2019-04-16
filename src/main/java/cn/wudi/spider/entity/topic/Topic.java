package cn.wudi.spider.entity.topic;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author wudi
 */
@Getter
@Setter
@Accessors(chain = true)
public class Topic {

  String topic;

  List<TopicContent> topicContents;
}
