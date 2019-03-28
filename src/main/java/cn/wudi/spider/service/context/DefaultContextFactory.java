package cn.wudi.spider.service.context;

import cn.wudi.spider.entity.CommonQuery;

/**
 * @author wudi
 */
public class DefaultContextFactory implements ContextFactory {

  @Override
  public <T extends CommonQuery> Context<T> newContext(T query) {
    String topicTitle = query.getTopicTitle();
    MemStore values = new MemStore();
    return new DefaultContext<T>()
        .setTopicTitle(topicTitle)
        .setValues(values)
        .setQuery(query);
  }
}
