package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.Result;
import org.springframework.stereotype.Repository;

/**
 * @author wudi
 */
public interface SpiderService {

  <T extends CommonQuery> Result create(T query);

  <T extends CommonQuery> Result summary(T query);
}
