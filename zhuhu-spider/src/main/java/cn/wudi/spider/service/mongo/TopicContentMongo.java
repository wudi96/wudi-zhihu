package cn.wudi.spider.service.mongo;

import cn.wudi.spider.entity.topic.TopicContent;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author wudi
 */
@Service
public class TopicContentMongo {

  @Resource
  private MongoTemplate mongoTemplate;

  public void set(TopicContent t) {
    Query query = Query.query(Criteria.where("questionAnswerId").is(t.getQuestionAnswerId()));
    Update update = new Update();
    update.set("question", t.getQuestion());
    update.set("answer", t.getAnswer());
    update.set("topicId", t.getTopicId());
    mongoTemplate.upsert(query, update, TopicContent.class);
  }

  public List<TopicContent> get(String s) {
    Query query = Query.query(Criteria.where("topicId").is(s));
    return mongoTemplate.find(query, TopicContent.class);
  }
}
