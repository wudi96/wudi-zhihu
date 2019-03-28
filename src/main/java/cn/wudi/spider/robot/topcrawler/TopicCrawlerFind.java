package cn.wudi.spider.robot.topcrawler;

import cn.wudi.spider.entity.Answer;
import cn.wudi.spider.entity.Question;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.Topic;
import cn.wudi.spider.entity.TopicContent;
import cn.wudi.spider.http.Response;
import cn.wudi.spider.robot.base.Find;
import cn.wudi.spider.utils.RegexUtils;
import java.util.ArrayList;
import java.util.regex.Matcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author wudi
 */
public class TopicCrawlerFind extends Find {

  public Result<Topic> findTopicContext() {
    String topicTitle = topicTitle();
    String topicUrl = topicUrl();
    String topicAnswerNum = topicAnswerNum();
    int topAnswerNumInt = Integer.parseInt(topicAnswerNum);

    Topic topic = new Topic();
    topic.setTopic(topicTitle);

    ArrayList<TopicContent> topicContents = new ArrayList<>();

    Response topicContentResponse = request().url(topicUrl).GET().send();
    String topicContentStr = topicContentResponse.string();
    logger().writeFile("topicContext.html", topicContentStr);

    Document doc = Jsoup.parse(topicContentStr);
    Elements item = doc.select(".ContentItem").select(".AnswerItem");
    int size = item.size() > topAnswerNumInt ? topAnswerNumInt : item.size();
    for (int i = 0; i < size; i++) {
      topicContents.add(crawlerTopContext(item.get(i)));
    }
    topic.setTopicContents(topicContents);
    return Result.ok(topic);
  }

  private TopicContent crawlerTopContext(Element item) {
    TopicContent topicContent = new TopicContent();
    ArrayList<Answer> answerList = new ArrayList<>();
    String href = item.selectFirst("div[itemprop=zhihu:question]").selectFirst("a")
        .attr("href");
    Matcher matcher = RegexUtils.getMatcher(href, "\\d+");
    Question question = new Question();
    if (matcher.find()) {
      question.setId(matcher.group());
    }
    topicContent.setQuestion(question);
    Answer answer = new Answer();
    if (matcher.find()) {
      answer.setId(matcher.group());
    }
    answerList.add(answer);
    topicContent.setAnswer(answerList);
    return topicContent;
  }
}
