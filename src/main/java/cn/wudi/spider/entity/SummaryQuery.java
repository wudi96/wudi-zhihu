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
public class SummaryQuery extends CommonQuery{
  private String topicId;
}
