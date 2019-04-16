package cn.wudi.spider.robot.scheduler;

import static cn.wudi.spider.constant.Constant.ANSWER_SUFFIX;
import static cn.wudi.spider.constant.Constant.INCLUDE;
import static cn.wudi.spider.constant.Constant.QUESTION_PREFIX;
import static cn.wudi.spider.constant.Constant.TOP_API_PREFIX;
import static cn.wudi.spider.constant.Constant.TOP_API_SUFFIX;

import cn.wudi.spider.entity.topic.Answer;
import cn.wudi.spider.entity.topic.Question;
import cn.wudi.spider.entity.topic.TopicContent;
import cn.wudi.spider.http.Response;
import cn.wudi.spider.robot.base.Find;
import cn.wudi.spider.service.mongo.TopicContentMongo;
import cn.wudi.spider.utils.RegexUtils;
import cn.wudi.spider.utils.TimeFormatUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author wudi
 */
@Getter
@Setter
@Accessors(chain = true)
public class Task implements Runnable {

  private String topicTitle;
  private String topicId;
  private String topicAnswerNum;
  private String threadName;
  private Find find;
  private TopicContentMongo topicContentMongo;

  public Task(Find find, TopicContentMongo topicContentMongo,
      String topicTitle,
      String topicId, String topicAnswerNum) {
    this.topicTitle = topicTitle;
    this.topicId = topicId;
    this.topicAnswerNum = topicAnswerNum;
    this.threadName =
        "thread_pool_" + this.topicTitle + "_" + this.topicId + "_" + this.topicAnswerNum;
    this.find = find;
    this.topicContentMongo = topicContentMongo;
  }

  @Override
  public void run() {
    getTopic();
  }

  private void getTopic() {
    int topAnswerNumInt = Integer.parseInt(topicAnswerNum);

    int tenNum = topAnswerNumInt / 10;
    int tenElseNum = topAnswerNumInt % 10;
    String topicContextUrl = TOP_API_PREFIX + topicId + TOP_API_SUFFIX;

    ArrayList<TopicContent> topicContents = new ArrayList<>();
    for (int i = 0; i < tenNum; i++) {
      Response topicContentResponse = getResponse(topicContextUrl, i, 10);
      this.find.logger().println(topicContentResponse.url().toString());
      String topicContentStr = topicContentResponse.string();
      this.find.logger().writeFile("topCon10_" + i + ".json", topicContentStr);
      parseTopCont(topicContents, topicContentStr);
    }

    Response topicContentElseResponse = getResponse(topicContextUrl, tenNum, tenElseNum);
    this.find.logger().println(topicContentElseResponse.url().toString());
    String topicContentElseStr = topicContentElseResponse.string();
    this.find.logger().writeFile("topConElse.json", topicContentElseStr);
    parseTopCont(topicContents, topicContentElseStr);
    topicContents.forEach(o -> topicContentMongo.set(o));
  }

  private void parseTopCont(ArrayList<TopicContent> topicContents, String topicContentStr) {
    JSONObject jsonObject = JSONObject.parseObject(topicContentStr);
    JSONArray data = jsonObject.getJSONArray("data");
    for (Object datum : data) {
      TopicContent topicContent = new TopicContent();

      Question question = new Question();
      JSONObject o = (JSONObject) datum;
      JSONObject questionJson = (JSONObject) JSONPath.eval(o, "$.target.question");
      if (questionJson == null) {
        return;
      }
      String questionId = questionJson.getString("id");
      question.setId(questionId);
      question.setCrawlTime(TimeFormatUtils.valueOf(new Date(), "yyyy-MM-dd HH:mm:ss"));
      String questionUrl = QUESTION_PREFIX + questionId;
      question.setUrl(questionUrl);
      question.setTitle(questionJson.getString("title"));
      //问题细节
      getQuestionDetail(question, questionUrl);
      topicContent.setQuestion(question);

      ArrayList<Answer> answerList = new ArrayList<>();
      String answerId = String.valueOf(JSONPath.eval(o, "$.target.id"));
      String answerContent = (String) JSONPath.eval(o, "$.target.content");
      String voteCount = String.valueOf(JSONPath.eval(o, "$.target.voteup_count"));
      String commentCount = String.valueOf(JSONPath.eval(o, "$.target.comment_count"));
      String createdTime = String.valueOf(JSONPath.eval(o, "$.target.created_time"));
      String updatedTime = String.valueOf(JSONPath.eval(o, "$.target.updated_time"));
      Answer answer = new Answer();
      answer.setId(answerId);
      answer.setUrl(questionUrl + ANSWER_SUFFIX + answerId);
      answer.setVoteCount(voteCount);
      answer.setCommentCount(commentCount);
      answer.setCreateTime(TimeFormatUtils
          .valueOf(new Date(Long.valueOf(createdTime + "000")), "yyyy-MM-dd HH:mm:ss"));
      answer.setModifyTime(TimeFormatUtils
          .valueOf(new Date(Long.valueOf(updatedTime + "000")), "yyyy-MM-dd HH:mm:ss"));
      answer.setContent(answerContent);
      answerList.add(answer);
      topicContent.setAnswer(answerList);

      topicContent.setQuestionId(questionId);
      topicContent.setAnswerId(answerId);
      topicContent.setTopicId(topicId);
      topicContents.add(topicContent);
    }
  }

  private void getQuestionDetail(Question question, String questionUrl) {
    Response response = this.find.request().GET().url(questionUrl).send();
    String string = response.string();
    this.find.logger().writeFile(questionUrl + ".html", string);
    Document parse = Jsoup.parse(string);
    Elements questionHeader = parse.select(".QuestionHeaderActions");
    Matcher questionHeaderMatcher = RegexUtils.getMatcher(questionHeader.text(), "(\\d+) 条评论");
    if (questionHeaderMatcher.find()) {
      question.setCommentCount(questionHeaderMatcher.group(1));
    }
    Elements numberBoards = parse.select(".NumberBoard-itemName");
    for (Element numberBoard : numberBoards) {
      if ("关注者".equals(numberBoard.text())) {
        question.setStareCount(numberBoard.nextElementSibling().text());
      }
      if ("被浏览".equals(numberBoard.text())) {
        question.setBrowseCount(numberBoard.nextElementSibling().text());
      }
    }
    ArrayList<String> tags = new ArrayList<>();
    Elements topicLinks = parse.select(".TopicLink");
    for (Element topicLink : topicLinks) {
      tags.add(topicLink.text());
    }
    question.setTags(tags);

    Elements text = parse.select(".QuestionHeader-detail").select(".RichText");
    question.setDetail(text.text());
  }

  private Response getResponse(String topicContextUrl, int tenNum, int tenElseNum) {
    return this.find.request().GET().url(topicContextUrl)
        .query("include", INCLUDE)
        .query("offset", String.valueOf(tenNum * 10))
        .query("limit", String.valueOf(tenElseNum))
        .send();
  }
}
