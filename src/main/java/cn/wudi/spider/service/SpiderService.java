package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.Result;

/**
 * @author wudi
 */
public interface SpiderService {

  <T extends CommonQuery> Result create(T query);
}
