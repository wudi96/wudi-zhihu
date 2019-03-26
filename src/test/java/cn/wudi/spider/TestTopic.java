package cn.wudi.spider;

import cn.wudi.spider.entity.Question;
import cn.wudi.spider.entity.Topic;
import java.util.ArrayList;
import org.junit.Test;
import cn.wudi.spider.entity.Answer;

public class TestTopic {

  @Test
  public void creatTopic() {
    ArrayList<Answer> answers = new ArrayList<>();
    answers.add(new Answer());
    new Topic().setAnswer(answers).setQuestion(new Question());
  }
}
