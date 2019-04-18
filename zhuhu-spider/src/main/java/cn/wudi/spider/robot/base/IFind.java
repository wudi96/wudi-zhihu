package cn.wudi.spider.robot.base;

import cn.wudi.spider.service.context.Context;

/**
 * @author wudi
 */
public interface IFind {

  /**
   * @return 爬虫上下文
   */
  Context getContext();

  /**
   * 设置一个爬虫的上下文
   *
   * @param context 爬虫上下文
   */
  IFind setContext(Context context);
}
