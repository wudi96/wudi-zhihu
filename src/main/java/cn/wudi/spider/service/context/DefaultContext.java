package cn.wudi.spider.service.context;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DefaultContext<T> implements Context<T> {

  // 上下文额外信息
  private Store values;

  private String topicId;

  private String topicTitle;

  /**
   * 传入参数
   */
  private T query;
}
