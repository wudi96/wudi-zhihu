package cn.wudi.spider.robot.topsummary;

import static cn.wudi.spider.constant.Constant.TOP_PREFIX;
import static cn.wudi.spider.constant.Constant.TOP_SUFFIX;

import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.SummaryResult;
import cn.wudi.spider.http.Response;
import cn.wudi.spider.robot.base.Find;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author wudi
 */
public class TopicSummaryFind extends Find {

  public Result<SummaryResult> findTopicSummary(String topicTitle, String topicId) {
    SummaryResult summaryResult = new SummaryResult();
    summaryResult.setTopicTitle(topicTitle);
    summaryResult.setTopicId(topicId);
    String topicUrl = TOP_PREFIX + topicId + TOP_SUFFIX;
    summaryResult.setTopicUrl(topicUrl);
    Response topSumResponse = request().GET().url(topicUrl).send();
    String topSumStr = topSumResponse.string();
    logger().writeFile("topSum.html", topSumStr);
    Document doc = Jsoup.parse(topSumStr);
    Elements elements = doc.select(".NumberBoard-itemName");
    for (Element element : elements) {
      if ("关注者".equals(element.text())) {
        summaryResult.setTopicAttentionNum(element.nextElementSibling().text());
      }
      if ("问题数".equals(element.text())) {
        summaryResult.setTopicAnswerNum(element.nextElementSibling().text());
      }
    }
    return Result.ok(summaryResult);
  }
}
