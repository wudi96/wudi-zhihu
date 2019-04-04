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


  @Autowired
  public TopicCtrl(SpiderService spiderService, TopicTitleRedis topicTitleRedis) {
    this.spiderService = spiderService;
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  Result create(@RequestBody TopicQuery topicQuery) {
    return Result.ok(spiderService.create(topicQuery));
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
