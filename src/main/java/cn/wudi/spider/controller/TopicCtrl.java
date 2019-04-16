package cn.wudi.spider.controller;

import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.TopicQuery;
import cn.wudi.spider.service.SpiderServiceImpl;
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

  private final SpiderServiceImpl spiderServiceImpl;


  @Autowired
  public TopicCtrl(SpiderServiceImpl spiderServiceImpl, TopicTitleRedis topicTitleRedis) {
    this.spiderServiceImpl = spiderServiceImpl;
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  Result create(@RequestBody TopicQuery topicQuery) {
    return Result.ok(spiderServiceImpl.create(topicQuery));
  }

  @RequestMapping(value = "/summary", method = RequestMethod.POST)
  Result summary(@RequestBody TopicQuery topicQuery) {
    return Result.ok(spiderServiceImpl.summary(topicQuery));
  }

  @RequestMapping(value = "/crawler", method = RequestMethod.POST)
  Result crawler(@RequestBody TopicQuery topicQuery) {
    return Result.ok(spiderServiceImpl.crawler(topicQuery));
  }

  @RequestMapping(value = "/result", method = RequestMethod.POST)
  Result result(@RequestBody TopicQuery topicQuery) {
    return Result.ok(spiderServiceImpl.result(topicQuery));
  }

  @RequestMapping(value = "/status", method = RequestMethod.POST)
  Result status(@RequestBody TopicQuery topicQuery) {
    return Result.ok(spiderServiceImpl.status(topicQuery));
  }

  @RequestMapping(value = "/clear", method = RequestMethod.GET)
  Result clear() {
    return Result.ok(spiderServiceImpl.clear());
  }
}
