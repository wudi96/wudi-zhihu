package cn.wudi.spider.robot;

import cn.wudi.spider.entity.Result;
import cn.wudi.spider.robot.base.Find;
import java.util.HashMap;

/**
 * @author wudi
 */
public class TopicStatus extends Find {

  public Result status(HashMap<String, String> threadMap) {
    String s = "thread_pool_" + topicTitle() + "_" + topicId() + "_" + topicAnswerNum();
    String result = threadMap.getOrDefault(s, "error");
    return Result.ok(result);
  }
}
