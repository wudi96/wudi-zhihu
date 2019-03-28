package cn.wudi.spider.robot.topcrawler;

import static cn.wudi.spider.constant.Constant.INCLUDE;
import static cn.wudi.spider.constant.Constant.TOP_API_PREFIX;
import static cn.wudi.spider.constant.Constant.TOP_API_SUFFIX;

import cn.wudi.spider.entity.Answer;
import cn.wudi.spider.entity.Question;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.Topic;
import cn.wudi.spider.entity.TopicContent;
import cn.wudi.spider.http.Response;
import cn.wudi.spider.robot.base.Find;
import java.util.ArrayList;

/**
 * @author wudi
 */
public class TopicCrawlerFind extends Find {

  public Result<Topic> findTopicContext() {
    String topicTitle = topicTitle();
    String topicId = topicId();
    String topicAnswerNum = topicAnswerNum();
    int topAnswerNumInt = Integer.parseInt(topicAnswerNum);

    String topicContextUrl = TOP_API_PREFIX + topicId + TOP_API_SUFFIX;

    Response topicContentResponse = request().GET().url(topicContextUrl)
        .query("include", INCLUDE)
        .query("offset", topicAnswerNum)
        .query("limit", "10")
        .send();
    logger().println(topicContentResponse.url().toString());
    logger().writeFile("topCon.json", topicContentResponse.string());

    Topic topic = new Topic();
    topic.setTopic(topicTitle);

    ArrayList<TopicContent> topicContents = new ArrayList<>();
    topicContents.add(crawlerTopContext());
    topic.setTopicContents(topicContents);
    return Result.ok(topic);
  }

  private TopicContent crawlerTopContext() {
    TopicContent topicContent = new TopicContent();
    ArrayList<Answer> answerList = new ArrayList<>();
    Question question = new Question();
    topicContent.setQuestion(question);
    Answer answer = new Answer();
    answerList.add(answer);
    topicContent.setAnswer(answerList);
    return topicContent;
  }
}
