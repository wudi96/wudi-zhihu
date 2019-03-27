package cn.wudi.spider.controller;

import cn.wudi.spider.entity.CreateQuery;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.SummaryQuery;
import cn.wudi.spider.service.SpiderServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wudi
 */
@RestController
public class TopicCtrl {

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  Result create(@RequestBody CreateQuery createQuery) {
    return Result.ok(new SpiderServiceImpl().create(createQuery));
  }

  @RequestMapping(value = "/summary", method = RequestMethod.POST)
  Result create(@RequestBody SummaryQuery summaryQuery) {
    return Result.ok(new SpiderServiceImpl().summary(summaryQuery));
  }
}
