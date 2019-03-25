import entity.Answer;
import entity.Question;
import entity.Topic;
import java.util.ArrayList;
import org.junit.Test;

public class TestTopic {

  @Test
  public void creatTopic() {
    ArrayList<Answer> answers = new ArrayList<>();
    answers.add(new Answer());
    new Topic().setAnswer(answers).setQuestion(new Question());
  }
}
