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
public class SummaryResult {

  private String topicId;
  private String topicTitle;
  private String topicAnswerNum;
  private String topicUrl;
}
