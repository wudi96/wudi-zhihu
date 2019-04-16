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
public class TopicContent {

  Question question;

  List<Answer> answer;

  String topicId;

  String questionId;

  String answerId;
}
