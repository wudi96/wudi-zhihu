package cn.wudi.spider.service.context;

import cn.wudi.spider.entity.CommonQuery;

/**
 * @author wudi
 */
public interface ContextFactory {

  /**
   * 根据入参和任务构建一个崭新的上下文
   *
   * @param query 入参
   * @return 上下文
   */
  <T extends CommonQuery> Context<T> newContext(T query);
}
