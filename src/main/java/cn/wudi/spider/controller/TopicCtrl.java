package cn.wudi.spider.controller;

import cn.wudi.spider.entity.CreateResult;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.TopicQuery;
import cn.wudi.spider.service.SpiderService;
import cn.wudi.spider.service.redis.TopicTitleRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wudi
 */
@RestController
public class TopicCtrl {

  private final SpiderService spiderService;

  private final TopicTitleRedis topicTitleRedis;

  @Autowired
  public TopicCtrl(SpiderService spiderService, TopicTitleRedis topicTitleRedis) {
    this.spiderService = spiderService;
    this.topicTitleRedis = topicTitleRedis;
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  Result create(@RequestBody TopicQuery topicQuery) {
    String topicId = topicTitleRedis.get(topicQuery);
    if (topicId != null) {
      return Result.ok(new CreateResult()
          .setTopicTitle(topicQuery.getTopicTitle())
          .setTopicId(topicId));
    }
    Result data = spiderService.create(topicQuery);
    Object o = data.getData();
    if (o instanceof CreateResult) {
      CreateResult result = (CreateResult) o;
      topicTitleRedis.set(result);
      String resultTopicTitle = result.getTopicTitle();
      String topicTitleQuery = topicQuery.getTopicTitle();
      if (!resultTopicTitle.equals(topicTitleQuery)) {
        topicTitleRedis
            .set(new CreateResult().setTopicTitle(topicTitleQuery)
                .setTopicId(result.getTopicId()));
      }
    }
    return Result.ok(data);
  }

  @RequestMapping(value = "/summary", method = RequestMethod.POST)
  Result summary(@RequestBody TopicQuery topicQuery) {
    return Result.ok(spiderService.summary(topicQuery));
  }

  @RequestMapping(value = "/crawler", method = RequestMethod.POST)
  Result crawler(@RequestBody TopicQuery topicQuery) {
    return Result.ok(spiderService.crawler(topicQuery));
  }
}
