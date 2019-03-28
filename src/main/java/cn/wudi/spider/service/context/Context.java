package cn.wudi.spider.service.context;

/**
 * @author wudi
 */
public interface Context<T> {

  /**
   * 上下文额外信息
   */
  Store getValues();

  /**
   * topicId
   */
  String getTopicId();

  /**
   * topicTitle
   */
  String getTopicTitle();

  /**
   * topicUrl
   */
  String getTopicUrl();

  /**
   * 入参
   */
  T getQuery();
}
