package cn.wudi.spider.service;

import cn.wudi.spider.entity.CommonQuery;
import cn.wudi.spider.entity.CreateResult;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.robot.topid.FindTopicId;

/**
 * @author wudi
 */
public class SpiderServiceImpl implements SpiderService {

  @Override
  public <T extends CommonQuery> Result create(T query) {
    CreateResult createResult = new CreateResult();
    createResult.setTopicTitle(query.getTopicTitle());
    createResult.setTopicId(new FindTopicId().findTopId(query.getTopicTitle()));
    return Result.ok(createResult);
  }
}
