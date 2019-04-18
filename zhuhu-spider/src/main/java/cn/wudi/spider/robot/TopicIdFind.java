package cn.wudi.spider.robot;

import cn.wudi.spider.constant.Constant;
import cn.wudi.spider.entity.CreateResult;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.http.Response;
import cn.wudi.spider.robot.base.Find;
import cn.wudi.spider.utils.RegexUtils;
import java.util.regex.Matcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author wudi
 */
public class TopicIdFind extends Find {

  public Result findTopId() {
    String topicTitle = topicTitle();
    CreateResult createResult = new CreateResult();
    Response searchTopicResponse = request().GET().url(Constant.SEARCH_URL)
        .query("type", "content")
        .query("q", topicTitle)
        .send();
    String searchTopicStr = searchTopicResponse.string();
    logger().writeFile("search.html", searchTopicStr);
    Document doc = Jsoup.parse(searchTopicStr);
    Elements elements = doc.select(".SearchItem-type");
    Element topEle = null;
    for (Element element : elements) {
      if ("话题".equals(element.text())) {
        topEle = element.previousElementSibling();
        break;
      }
    }
    if (topEle == null) {
      return Result.fail("没有此话题");
    }
    String href = topEle.select("a").attr("href");
    String topSearch = topEle.text();
    Matcher matcher = RegexUtils.getMatcher(href, "\\d+");
    if (matcher.find()) {
      return Result.ok(createResult.setTopicId(matcher.group()).setTopicTitle(topSearch));
    }
    return Result.ok(createResult);
  }
}
