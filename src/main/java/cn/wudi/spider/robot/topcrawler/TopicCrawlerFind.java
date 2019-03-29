package cn.wudi.spider.robot.topcrawler;

import static cn.wudi.spider.constant.Constant.ANSWER_SUFFIX;
import static cn.wudi.spider.constant.Constant.INCLUDE;
import static cn.wudi.spider.constant.Constant.QUESTION_PREFIX;
import static cn.wudi.spider.constant.Constant.TOP_API_PREFIX;
import static cn.wudi.spider.constant.Constant.TOP_API_SUFFIX;

import cn.wudi.spider.entity.Answer;
import cn.wudi.spider.entity.Question;
import cn.wudi.spider.entity.Result;
import cn.wudi.spider.entity.Topic;
import cn.wudi.spider.entity.TopicContent;
import cn.wudi.spider.http.Response;
import cn.wudi.spider.robot.base.Find;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
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

    int tenNum = topAnswerNumInt / 10;
    int tenElseNum = topAnswerNumInt % 10;
    String topicContextUrl = TOP_API_PREFIX + topicId + TOP_API_SUFFIX;

    ArrayList<TopicContent> topicContents = new ArrayList<>();
    for (int i = 0; i < tenNum; i++) {
      Response topicContentResponse = getResponse(topicContextUrl, i, 10);
      logger().println(topicContentResponse.url().toString());
      String topicContentStr = topicContentResponse.string();
      logger().writeFile("topCon10_" + tenNum + ".json", topicContentStr);
      parseTopCont(topicContents, topicContentStr);
    }

    Response topicContentElseResponse = getResponse(topicContextUrl, tenNum, tenElseNum);
    logger().println(topicContentElseResponse.url().toString());
    String topicContentElseStr = topicContentElseResponse.string();
    logger().writeFile("topConElse.json", topicContentElseStr);
    parseTopCont(topicContents, topicContentElseStr);

    Topic topic = new Topic();
    topic.setTopic(topicTitle);
    topic.setTopicContents(topicContents);
    return Result.ok(topic);
  }

  private void parseTopCont(ArrayList<TopicContent> topicContents, String topicContentStr) {
    JSONObject jsonObject = JSONObject.parseObject(topicContentStr);
    JSONArray data = jsonObject.getJSONArray("data");
    for (Object datum : data) {
      TopicContent topicContent = new TopicContent();

      Question question = new Question();
      JSONObject o = (JSONObject) datum;
      JSONObject questionJson = (JSONObject) JSONPath.eval(o, "$.target.question");
      String questionId = questionJson.getString("id");
      question.setId(questionId);
      question.setTitle(questionJson.getString("title"));
      String questionUrl = QUESTION_PREFIX + questionId;
      question.setUrl(questionUrl);
      topicContent.setQuestion(question);

      ArrayList<Answer> answerList = new ArrayList<>();
      String answerId = String.valueOf(JSONPath.eval(o, "$.target.id"));
      String answerContent = (String) JSONPath.eval(o, "$.target.content");
      Answer answer = new Answer();
      answer.setId(answerId);
      answer.setUrl(questionUrl + ANSWER_SUFFIX + answerId);
      answer.setContent(answerContent);
      answerList.add(answer);
      topicContent.setAnswer(answerList);
      topicContents.add(topicContent);
    }
  }

  private Response getResponse(String topicContextUrl, int tenNum, int tenElseNum) {
    return request().GET().url(topicContextUrl)
        .query("include", INCLUDE)
        .query("offset", String.valueOf(tenNum * 10))
        .query("limit", String.valueOf(tenElseNum))
        .send();
  }
}
