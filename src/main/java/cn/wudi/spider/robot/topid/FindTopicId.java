package cn.wudi.spider.robot.topid;

import static cn.wudi.spider.constant.Constant.SEARCH_URL;

import cn.wudi.spider.http.Response;
import cn.wudi.spider.robot.base.Find;

/**
 * @author wudi
 */
public class FindTopicId extends Find {

  public String findTopId(String topicTitle) {
    Response searchTopicResponse = request().GET().url(SEARCH_URL)
        .query("type", "content")
        .query("q", topicTitle)
        .send();
    logger().writeFile("search.html", searchTopicResponse.string());
    return "19570679";
  }
}
