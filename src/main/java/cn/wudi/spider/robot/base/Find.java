package cn.wudi.spider.robot.base;

import cn.wudi.spider.entity.TopicQuery;
import cn.wudi.spider.http.Request;
import cn.wudi.spider.service.context.Context;
import cn.wudi.spider.service.logger.ILogger;
import com.alibaba.fastjson.JSON;

/**
 * @author wudi
 */
public class Find extends AbstractFind {

  private Context<TopicQuery> context;

  public Request request() {
    return new Request().client(getClient());
  }

  public ILogger logger() {
    return getLogger();
  }

  @Override
  public Context<TopicQuery> getContext() {
    return context;
  }

  @Override
  public Find setContext(Context context) {
    if (!(context.getQuery() instanceof TopicQuery)) {
      throw new ClassCastException("can't convert to TopicQuery: \n" + JSON.toJSONString(context));
    }
    //noinspection unchecked
    this.context = context;
    return this;
  }

  public String topicTitle() {
    return getContext().getQuery().getTopicTitle();
  }

  public String topicId() {
    return getContext().getQuery().getTopicId();
  }

  public String topicUrl() {
    return getContext().getQuery().getTopicUrl();
  }
}
