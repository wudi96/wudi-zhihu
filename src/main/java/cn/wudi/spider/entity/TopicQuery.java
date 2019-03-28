package cn.wudi.spider.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author wudi
 */
@Getter
@Setter
@Accessors(chain = true)
public class TopicQuery extends CommonQuery {

  private String topicTitle;
  private String topicId;
  private String topicUrl;
  private String topicAnswerNum;
}
