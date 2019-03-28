package cn.wudi.spider.controller;

import cn.wudi.spider.entity.CreateQuery;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.SummaryQuery;
import cn.wudi.spider.service.SpiderService;
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
  public TopicCtrl(SpiderService spiderService) {
    this.spiderService = spiderService;
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  Result create(@RequestBody CreateQuery createQuery) {
    return Result.ok(spiderService.create(createQuery));
  }

  @RequestMapping(value = "/summary", method = RequestMethod.POST)
  Result create(@RequestBody SummaryQuery summaryQuery) {
    return Result.ok(spiderService.summary(summaryQuery));
  }
}
